package com.wty.project.model.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class InterfaceJoinLeftNumVO implements Serializable {
    private Integer id;
    private Long userId;
    private Long interfaceId;
    private Integer leftNum;
    private String name;
    private String description;
    private String avatarUrl;

    private static final long serialVersionUID = 1L;
}
