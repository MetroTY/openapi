package com.wty.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import fun.wty.openapicommon.model.entity.InterfaceInfo;

/**
* @author wty
* @description 针对表【interface_info】的数据库操作Service
* @createDate 2023-09-11 16:26:02
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {
     void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
