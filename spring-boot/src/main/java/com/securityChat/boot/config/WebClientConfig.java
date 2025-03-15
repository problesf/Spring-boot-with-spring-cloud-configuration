package com.securityChat.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.securityChat.boot.service.KeycloakTokenService;

@Configuration
public class WebClientConfig {

    private final KeycloakTokenService keycloakTokenService;

    public WebClientConfig(KeycloakTokenService keycloakTokenService) {
        this.keycloakTokenService = keycloakTokenService;
    }

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl("http://localhost:8080")
                .defaultHeaders(headers -> {
                    String token = keycloakTokenService.getAccessToken();
                    if (token == null) {
                        System.err.println("🚨 Advertencia: No se pudo obtener el token de Keycloak, se omite el header Authorization.");
                    } else {
                        headers.setBearerAuth(token);
                    }
                })
                .build();
    }
}
