package com.openapi;

import com.openapi.client.WtyClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 负责去调用接口的方法
 */
@ComponentScan
@Configuration
@ConfigurationProperties(prefix = "wtyapi.client")
@Data
public class HttpClient {
    private String accessKey;
    private String secretKey;

    @Bean
    public WtyClient wtyClient(){
        return new WtyClient(accessKey, secretKey);
    }
}
