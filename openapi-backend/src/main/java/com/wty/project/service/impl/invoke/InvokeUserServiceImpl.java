package com.wty.project.service.impl.invoke;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.openapi.exception.ErrorCode;
import com.wty.project.exception.BusinessException;
import com.wty.project.mapper.UserMapper;
import fun.wty.openapicommon.model.entity.User;
import fun.wty.openapicommon.service.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class InvokeUserServiceImpl extends ServiceImpl<UserMapper, User> implements InnerUserService {
    @Override
    public User getInvokeUser(String accessKey) {
        isParamBlank(accessKey);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("accessKey", accessKey);
        User one = this.getOne(wrapper);
        return one;
    }

    public void isParamBlank(String param) {
        if (StringUtils.isBlank(param)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, String.format("%s 不存在", param));
        }
    }

}
