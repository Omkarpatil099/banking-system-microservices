package com.omkar.account_service.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UserServiceClient {

    private final RestClient restClient;

    public UserServiceClient(RestClient.Builder builder) {

        this.restClient = builder
                .baseUrl("http://USER-SERVICE")
                .build();
    }

    public boolean userExists(Long userId) {

        try {

            restClient.get()
                    .uri("/api/users/{userId}", userId)
                    .retrieve()
                    .toBodilessEntity();

            return true;

        } catch (Exception e) {

            return false;
        }
    }
}