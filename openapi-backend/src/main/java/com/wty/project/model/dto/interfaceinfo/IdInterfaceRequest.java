package com.wty.project.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 根据id上下线接口
 *
 * @TableName product
 */
@Data
public class IdInterfaceRequest implements Serializable {
    /**
     * 用户ID
     */
    private Integer id;


    private static final long serialVersionUID = 1L;
}