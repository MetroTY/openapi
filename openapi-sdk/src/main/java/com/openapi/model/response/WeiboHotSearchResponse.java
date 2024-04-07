package com.openapi.model.response;

import lombok.Data;

import java.util.List;

/**
 * @author PYW
 */
@Data
public class WeiboHotSearchResponse {
    private List<WeiboHot> weibohotSearch;
}

