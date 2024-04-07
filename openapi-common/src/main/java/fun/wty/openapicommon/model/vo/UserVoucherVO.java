package fun.wty.openapicommon.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户凭证
 */
@Data
public class UserVoucherVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 签名认证id
     */
    private String secretId;

    /**
     * 签名认证key
     */
    private String secretKey;

    private static final long serialVersionUID = 1L;
}
