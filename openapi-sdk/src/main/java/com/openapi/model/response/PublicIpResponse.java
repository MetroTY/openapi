package com.openapi.model.response;

import lombok.Data;

/**
 * @author PYW
 */
@Data
public class PublicIpResponse {

    private String ipAddress;

    private String city;

    private String country;
}
