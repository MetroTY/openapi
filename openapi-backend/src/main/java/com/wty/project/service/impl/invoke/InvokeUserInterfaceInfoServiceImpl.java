package com.wty.project.service.impl.invoke;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openapi.exception.ErrorCode;
import com.wty.project.exception.BusinessException;
import com.wty.project.mapper.UserInterfaceInfoMapper;
import fun.wty.openapicommon.model.entity.UserInterfaceInfo;
import fun.wty.openapicommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class InvokeUserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo> implements InnerUserInterfaceInfoService {
    @Override
    public boolean invokeValid(Integer interfaceId, Long userId) {
        // 1.参数校验
        if (userId <= 0 || interfaceId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 2.校验：数据库leftNum > 0才进行数据库的更新，负责抛出异常
        QueryWrapper<UserInterfaceInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("userId", userId);
        wrapper.eq("interfaceId", interfaceId);
        UserInterfaceInfo userInterface = this.getOne(wrapper);
        return userInterface.getTotalNum() > 0;
    }

    @Override
    public boolean invokeNumPlus(Integer interfaceId, Long userId) {
        // 1.参数校验
        if (userId <= 0 || interfaceId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 2.数据库更新
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userId", userId);
        updateWrapper.eq("interfaceId", interfaceId);
        updateWrapper.setSql("totalNum = totalNum - 1");
        return this.update(updateWrapper);
    }
}
