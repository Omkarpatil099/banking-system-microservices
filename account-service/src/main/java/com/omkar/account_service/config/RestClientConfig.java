package com.omkar.account_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class RestClientConfig {

    @Bean
    @LoadBalanced
    public RestClient.Builder restClientBuilder() {

        return RestClient.builder()
                .requestInterceptor((request, body, execution) -> {

                    ServletRequestAttributes attributes =
                            (ServletRequestAttributes)
                                    RequestContextHolder.getRequestAttributes();

                    if (attributes != null) {

                        HttpServletRequest currentRequest =
                                attributes.getRequest();

                        String authorization =
                                currentRequest.getHeader("Authorization");

                        if (authorization != null) {
                            request.getHeaders()
                                    .set("Authorization", authorization);
                        }
                    }

                    return execution.execute(request, body);
                });
    }
}