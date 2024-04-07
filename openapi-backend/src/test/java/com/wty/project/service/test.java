package com.wty.project.service;

import com.wty.project.mapper.UserInterfaceInfoMapper;
import com.wty.project.model.vo.InterfaceJoinLeftNumVO;
import fun.wty.openapicommon.service.InnerUserInterfaceInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class test {

    @Resource
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Test
    public void test1(){
    }
}
