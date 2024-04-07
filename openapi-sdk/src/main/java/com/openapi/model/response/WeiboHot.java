package com.openapi.model.response;

import lombok.Data;

@Data
public class WeiboHot {
    private Integer hotNum;

    private Integer index;

    private String hotType;

    private String title;

    private String url;
}
