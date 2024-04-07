package com.wty.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openapi.exception.ErrorCode;
import com.wty.project.exception.BusinessException;
import com.wty.project.mapper.InterfaceInfoMapper;
import com.wty.project.service.InterfaceInfoService;
import fun.wty.openapicommon.model.entity.InterfaceInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author wty
 * @description 针对表【interface_info】的数据库操作Service实现
 * @createDate 2023-09-11 16:26:02
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {
    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        Integer id = interfaceInfo.getId();
        Long userId = interfaceInfo.getUserId();
        String name = interfaceInfo.getName();
        String method = interfaceInfo.getMethod();
        String url = interfaceInfo.getUrl();
        Integer status = interfaceInfo.getStatus();
        String requestHeader = interfaceInfo.getRequestHeader();
        String responseHeader = interfaceInfo.getResponseHeader();
        String requestParams = interfaceInfo.getRequestParams();
        String description = interfaceInfo.getDescription();

        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(name, method, url, requestHeader, responseHeader, description, requestParams) || ObjectUtils.anyNull(id, userId, status)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
    }
}




