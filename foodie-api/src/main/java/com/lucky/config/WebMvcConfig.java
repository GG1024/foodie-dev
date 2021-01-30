package com.lucky.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @FileName: WebMvcConfig.java
 * @description:
 * @author: 欧阳小广
 * @Date: 2021-01-30
 **/
@Configuration
public class WebMvcConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


}
