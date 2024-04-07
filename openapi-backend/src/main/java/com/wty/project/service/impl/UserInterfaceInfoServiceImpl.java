package com.wty.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openapi.exception.ErrorCode;
import com.wty.project.exception.BusinessException;
import com.wty.project.mapper.UserInterfaceInfoMapper;
import com.wty.project.service.UserInterfaceInfoService;
import fun.wty.openapicommon.model.entity.UserInterfaceInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
 * @author wty
 * @description 针对表【user_interface_info(用户接口关系表)】的数据库操作Service实现
 * @createDate 2023-09-18 20:20:58
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements UserInterfaceInfoService {

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        Integer id = userInterfaceInfo.getId();
        Long userId = userInterfaceInfo.getUserId();
        Integer interfaceId = userInterfaceInfo.getInterfaceId();
        int totalNum = userInterfaceInfo.getTotalNum();
        Integer status = userInterfaceInfo.getStatus();

        // 传递的参数为null
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 创建时，所有参数必须非空
        if (add) {
            if (ObjectUtils.anyNull(id, userId, interfaceId, totalNum, status) || userId <= 0 || interfaceId <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
    }
}




