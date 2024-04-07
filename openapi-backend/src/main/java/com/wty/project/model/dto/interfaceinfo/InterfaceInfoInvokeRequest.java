package com.wty.project.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 请求参数
     */
    private Map<String, Object> requestParams;

    private static final long serialVersionUID = 1L;
}