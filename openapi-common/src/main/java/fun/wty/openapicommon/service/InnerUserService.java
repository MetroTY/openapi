package fun.wty.openapicommon.service;

import fun.wty.openapicommon.model.entity.User;

/**
 * 用户服务
 *
 * @author PYW
 * @from www.panyuwen.top
 */
public interface InnerUserService {

    /**
     * 获取数据库中是否已分配给用户密钥（secretId）
     */
    User getInvokeUser(String secretId);
}
