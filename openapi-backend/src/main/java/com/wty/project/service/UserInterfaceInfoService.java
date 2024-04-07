package com.wty.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import fun.wty.openapicommon.model.entity.UserInterfaceInfo;

/**
* @author wty
* @description 针对表【user_interface_info(用户接口关系表)】的数据库操作Service
* @createDate 2023-09-18 20:20:58
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    /**
     * 校验新增参数
     * @param userInterfaceInfo
     * @param add
     */
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);
}
