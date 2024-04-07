package com.wty.project.filter;

import cn.hutool.core.util.ObjUtil;
import com.openapi.exception.ErrorCode;
import com.openapi.utils.SignUtils;
import com.wty.project.exception.BusinessException;
import com.wty.project.utils.RedissonLockUtil;
import fun.wty.openapicommon.model.entity.InterfaceInfo;
import fun.wty.openapicommon.model.entity.User;
import fun.wty.openapicommon.model.enums.InterfaceInfoStatusEnum;
import fun.wty.openapicommon.service.InnerInterfaceInfoService;
import fun.wty.openapicommon.service.InnerUserInterfaceInfoService;
import fun.wty.openapicommon.service.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.openapi.exception.ErrorCode.NOT_FOUND_ERROR;

@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @Autowired
    private RedissonLockUtil redissonLockUtil;

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    // 请求方法
    private String method = null;

    // 请求地址
    private String path = null;

    // 请求url
    private String url;

    // 客户端实际请求地址
    private String originalUrl;

    // 记录请求流量，获取请求内容长度
    private Long requestContentLength = null;

    // 记录响应流量，获取响应内容长度
    private Long responseContentLength = null;

    // 公网ip
    private String ipAddress = null;

    // userId
    private Long userId = null;

    // interfaceInfoId
    private Integer interfaceInfoId = null;

    // 响应状态码
    HttpStatus statusCode = null;

    private Long startTime = null;

    private Long endTime = null;

    // 请求响应时间
    private Long responseTime = null;

    private static final List<String> blackList = Arrays.asList("");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取当前时间戳Localdatatime的方式
        startTime = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
        // 1.请求日志
        log.info("GateWay网关全局过滤器请求日志信息==========>");
        ServerHttpRequest request = exchange.getRequest();
        // 获取请求时间
        // 记录请求流量，获取请求内容长度
        requestContentLength = request.getHeaders().getContentLength();
        // /api/name/user
        path = request.getPath().value();
        method = request.getMethod().toString();
        url = request.getURI().toString().trim();
        // 获取公网ip
        // todo 设置断点，看什么是公网ip
        HttpHeaders headers = request.getHeaders();
        ipAddress = headers.getFirst("X-Real-IP");
        String uri = headers.getFirst("X-Original-URI");
        String host = headers.getFirst("X-Original-Host");
        String scheme = headers.getFirst("X-Original-Scheme");

        originalUrl = scheme + "://" + host + uri;

        if (ipAddress == null || ipAddress.isEmpty()) {
            log.info("未携带X-Real-IP请求头，尝试获取");
            ipAddress = request.getRemoteAddress().getHostString();
        }
        if (ipAddress == null || ipAddress.isEmpty()) {
            throw new BusinessException(NOT_FOUND_ERROR, "获取公网ip失败");
        }
        log.info("请求唯一标识：" + request.getId());
        log.info("请求路径：" + path);
        log.info("请求实际url：" + url);
        log.info("原始地址originalUrl:{}", originalUrl);
        log.info("请求方式：" + method);
        log.info("QueryParams：" + request.getQueryParams());
        log.info("请求流量（bytes）：" + requestContentLength);
        String hostString = request.getLocalAddress().getHostString();
        log.info("请求来源地址：" + hostString);
        log.info("<=======================================");

        // 2.黑白名单
        if (blackList.contains(hostString)) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }

        // 3,用户鉴权（判断 ak、sk 是否合法）

        String accessKey = headers.getFirst("X-AccessKey");
        String nonce = headers.getFirst("X-Nonce");
        String body = headers.getFirst("X-Body");
        try {
            body = new String(body.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String timestamp = headers.getFirst("X-Timestamp");
        String sign = headers.getFirst("X-Sign");

        // Api密钥是否分配给用户 校验
        User invokeUser = null;  // 查找调用用用户接口的id
        invokeUser = innerUserService.getInvokeUser(accessKey);  // 老是报错，回调主服务的方法，证明dubbo的有效性
        if (ObjUtil.isEmpty(invokeUser)) {
            throw new BusinessException(NOT_FOUND_ERROR, "请求用户密钥不存在");
        }
        if (invokeUser == null) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "未查询到用户信息");
        }
        if (!invokeUser.getAccessKey().equals(accessKey)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "API密钥解析失败");
        }

        // 随机数不得大于一万
        if (Integer.parseInt(nonce) > 10000) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "API密钥解析失败");
        }

        // 防重发，时间和当前时间不得超过五分钟
        if (System.currentTimeMillis() - Long.parseLong(timestamp) > 300000) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "API密钥解析失败");
        }

        // 解析sign，查询secretKey是否和header中的一致
        String secretKeyInServer = invokeUser.getSecretKey();
        String signInServer = SignUtils.getSign(body, secretKeyInServer);
        userId = invokeUser.getId();
        // 检查请求中的签名是否为空，或者是否与服务器的签名不一致
        if (sign == null || !sign.equals(signInServer)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "API密钥解析失败");
        }
        // 5.请求的模拟接口是否存在？
        if ("GET".equals(method)) {
            MultiValueMap<String, String> queryParams = request.getQueryParams();
            log.info("GET请求参数：" + queryParams);
        } else if ("POST".equals(method)) {
            // 获取post请求的参数
            log.info("POST请求参数：");
        }
        // 数据库查询模拟接口是否存在，以及请求方法是否匹配
        InterfaceInfo interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(url, method);  // 改成url了，为什么前面是公网ip？
        if (ObjUtil.isEmpty(interfaceInfo)) {
            throw new BusinessException(NOT_FOUND_ERROR, "请求接口不存在");
        }
        // 判断接口是否可用
        if (InterfaceInfoStatusEnum.OFFLINE.getValue().equals(interfaceInfo.getStatus())) {
            throw new BusinessException(NOT_FOUND_ERROR, "接口未上线");
        }
        interfaceInfoId = interfaceInfo.getId();
        // 判断调用次数是否 > 0
        if(!innerUserInterfaceInfoService.invokeValid(interfaceInfoId, userId)){
            // 判断调用次数
            throw new BusinessException(NOT_FOUND_ERROR, "调用次数不足");
        }
        log.info("interfaceInfoId:{}", interfaceInfoId);
        // 6.请求转发，调用模拟接口
        // 7.响应日志

        return handleResponse(exchange, chain, interfaceInfoId, invokeUser.getId());
    }
    @Override
    public int getOrder() {
        return -1;
    }

    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, Integer interfaceId, Long userId) {
        // 获取原始的响应对象
        ServerHttpResponse originalResponse = exchange.getResponse();
        // 获取数据缓冲工厂
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        // 获取响应的状态码
        statusCode = originalResponse.getStatusCode();

        // 判断状态码是否为200 OK
        log.info("响应状态码：{}", statusCode.value());
        if (statusCode == HttpStatus.OK) {
            // 创建一个装饰后的响应对象(开始穿装备，增强能力)
            log.info("成功调用方法，开始响应处理");
            ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                // 重写writeWith方法，用于处理响应体的数据
                // 这段方法就是只要当我们的模拟接口调用完成之后,等它返回结果，
                // 就会调用writeWith方法,我们就能根据响应结果做一些自己的处理
                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                    log.info("响应体是否是Flux类型： {}", (body instanceof Flux));
                    // 判断响应体是否是Flux类型
                    if (body instanceof Flux) {
                        Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                        // 返回一个处理后的响应体
                        // (这里就理解为它在拼接字符串,它把缓冲区的数据取出来，一点一点拼接好)
                        return super.writeWith(fluxBody.collectList().flatMap(list -> {
                            log.info("响应结果：" + list);

                            // 调用成功后的处理逻辑,如果到这里其实接口都已经调用完成了!

                            // 接口调用次数+1
                            try {
                                // todo 设置断点，设置分布式锁，统一次数统计
                                redissonLockUtil.redissonDistributedLocks(10000, 5000, TimeUnit.MILLISECONDS, ("gateway_" + userId).intern(), () -> {
                                    log.info("调用请求后扣款方法：interfaceId:{}  userId:{}", interfaceId, userId);
                                    boolean invoke = innerUserInterfaceInfoService.invokeNumPlus(interfaceId, userId);  // 进行次数-1
                                }, ErrorCode.OPERATION_ERROR, "接口调用繁忙请稍后再试");
                            } catch (BusinessException e) {
                                return Mono.error(new BusinessException(e.getCode(), e.getMessage()));
                            }

                            log.info("网关调用接口调用次数+1: {}", interfaceId);

                            // 读取响应体的内容并转换为字节数组
                            byte[] content = null;
                            if (list.size() > 1 || list.isEmpty()) {
                                content = list.stream()
                                        .map(DataBuffer::asByteBuffer)
                                        .reduce((buffer1, buffer2) -> {
                                            ByteBuffer mergedBuffer = ByteBuffer.allocate(buffer1.remaining() + buffer2.remaining());
                                            mergedBuffer.put(buffer1);
                                            mergedBuffer.put(buffer2);
                                            mergedBuffer.flip();
                                            return mergedBuffer;
                                        })
                                        .orElse(ByteBuffer.allocate(0))
                                        .array();
                            } else {
                                // 处理单个数据块
                                DataBuffer dataBuffer = list.get(0);
                                // 读取响应体的内容并转换为字节数组
                                content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer); // 释放掉内存
                            }
                            // 在这里执行您的后续处理逻辑
                            // 读取响应体的内容并转换为字节数组
                            // 得到响应体
                            responseContentLength = Long.valueOf(content.length);
                            String data = new String(content, StandardCharsets.UTF_8);
                            // 得到响应体大小

                            // 打印日志
                            log.info("响应结果：" + data);

                            // 返回一个包含处理后的响应体的 DataBuffer
                            return Mono.just(bufferFactory.wrap(content))
                                    .doFinally(signalType -> logSave());
                        }));
                    } else {
                        log.error("网关处理响应异常", getStatusCode());
                    }
                    return super.writeWith(body);
                }
            };
            // 对于200 OK的请求,将装饰后的响应对象传递给下一个过滤器链,并继续处理(设置repsonse对象为装饰过的)
            // 记录成功日志
            // 获取响应长度
            return chain.filter(exchange.mutate().response(decoratedResponse).build());
        }
        // 对于非200 OK的请求，直接返回，进行降级处理
        return chain.filter(exchange)
                .doFinally(signalType -> logSave());
    }

    private void logSave() {
        log.info("开始存储日志......");
        endTime = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();
        if (startTime != null && endTime != null) {
            //计算响应时间
            responseTime = endTime - startTime;
            log.info("请求开始时间：{}，请求结束时间：{}，请求耗时：{}ms", startTime, endTime, responseTime);
        }
    }

}