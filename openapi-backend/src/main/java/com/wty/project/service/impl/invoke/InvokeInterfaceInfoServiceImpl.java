package com.wty.project.service.impl.invoke;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openapi.exception.ErrorCode;
import com.wty.project.exception.BusinessException;
import com.wty.project.mapper.InterfaceInfoMapper;
import fun.wty.openapicommon.model.entity.InterfaceInfo;
import fun.wty.openapicommon.service.InnerInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class InvokeInterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo> implements InnerInterfaceInfoService {
    /**
     * 根据url和method查询接口是否存在
     *
     * @param url
     * @param method
     * @return
     */
    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        if (StringUtils.isAnyBlank(url, method)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, String.format("%s %s 不存在", url, method));
        }
        QueryWrapper<InterfaceInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("url", url);
        wrapper.eq("method", method);
        return this.getOne(wrapper);
    }
}
