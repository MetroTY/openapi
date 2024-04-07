package com.wty.project.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wty.project.model.vo.InterfaceJoinLeftNumVO;
import fun.wty.openapicommon.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author wty
* @description 针对表【user_interface_info(用户接口关系表)】的数据库操作Mapper
* @createDate 2023-09-18 20:20:58
* @Entity com.wty.project.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
    List<InterfaceJoinLeftNumVO> listInterfaceJoinTotal(long userId);

    List<InterfaceJoinLeftNumVO> listInterfaceJoinTotalName(long userId, String name);
}




