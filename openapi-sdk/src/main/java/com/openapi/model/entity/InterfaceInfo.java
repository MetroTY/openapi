package com.openapi.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName interface_info
 */
@Data
public class InterfaceInfo implements Serializable {
    /**
     * 接口ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 接口方法
     */
    private String method;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 接口状态(0 未接入 1 接入使用)
     */
    private Integer status;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 接口描述
     */
    private String description;


    private static final long serialVersionUID = 1L;
}