package fun.wty.openapicommon.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户接口调用表
 */
@TableName(value ="user_interface_info")
@Data
public class UserInterfaceInfo implements Serializable {
    /**
     * 接口ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 调用用户ID
     */
    private Long userId;

    /**
     * 接口ID
     */
    private Integer interfaceId;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 0-正常 1-禁用
     */
    private Integer status;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 删除标志(0 未删除 1 已删除)
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}