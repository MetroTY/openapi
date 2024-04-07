package com.wty.project.model.dto.userInterfaceInfo;

import com.wty.project.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author wty
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserInterfaceInfoQueryRequest extends PageRequest implements Serializable {

    /**
     * 接口ID
     */
    private Integer id;

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

    /**
     * 状态
     */
    private Integer status;


    private static final long serialVersionUID = 1L;
}