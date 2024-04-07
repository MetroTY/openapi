package com.openapi.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户
 * @author PYW
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String userAccount;
    private String userPassword;
    private String userName;
    private String userAvatar;
    private String userProfile;
    private String userRole;
    private String accessKey;
    private String secretKey;
}
