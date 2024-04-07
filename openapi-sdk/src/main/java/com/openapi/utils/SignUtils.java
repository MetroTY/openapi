package com.openapi.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

public class SignUtils {
    /**
     * 拿到加密后的签名信息
     * @param body 加密的一些参数的要求：防重放
     * @param secretKey 密码
     * @return 加密后的密码
     */
    public static String getSign(String body, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        String sign = md5.digestHex(body + "." + secretKey);
        return sign;
    }
}
