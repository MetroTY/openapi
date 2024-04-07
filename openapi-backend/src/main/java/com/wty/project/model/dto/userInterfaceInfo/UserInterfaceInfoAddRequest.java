package com.wty.project.model.dto.userInterfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class UserInterfaceInfoAddRequest implements Serializable {
    /**
     * 调用用户ID
     */
    private Integer userId;

    /**
     * 接口ID
     */
    private Integer interfaceInfoId;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    private static final long serialVersionUID = 1L;
}