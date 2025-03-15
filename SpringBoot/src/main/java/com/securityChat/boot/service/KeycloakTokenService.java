package com.securityChat.boot.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.securityChat.boot.model.KeycloakResponse;
import com.securityChat.boot.model.OAuth2ClientProperties;

import jakarta.annotation.PostConstruct;

@Service
public class KeycloakTokenService {

    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties; 

    @Autowired
    private WebClient.Builder webClientBuilder;

    private WebClient webClient;
    private String accessToken;
    private Instant expiresAt;

    @PostConstruct
    public void init() {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
        System.out.println("🔄 Obteniendo token de Keycloak al iniciar...");
        refreshToken();
    }

    public String getAccessToken() {
        if (accessToken == null || Instant.now().isAfter(expiresAt)) {
            System.out.println("🔄 Token expirado o no existente. Obteniendo nuevo token...");
            refreshToken();
        }
        return accessToken;
    }

    private void refreshToken() {
        String keycloakUrl = "/realms/MiRealm/protocol/openid-connect/token";

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", oAuth2ClientProperties.getClientId());
        formData.add("client_secret", oAuth2ClientProperties.getClientSecret());
        formData.add("grant_type", "client_credentials");

        try {
            var response = webClient.post()
                    .uri(keycloakUrl)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .bodyValue(formData)
                    .retrieve()
                    .bodyToMono(KeycloakResponse.class)
                    .block();

            if (response != null) {
                this.accessToken = response.accessToken();
                this.expiresAt = Instant.now().plusSeconds(response.expiresIn());
                System.out.println("✅ Token obtenido exitosamente: " + accessToken);
            } else {
                System.err.println("🚨 Error: La respuesta de Keycloak fue nula.");
            }
        } catch (WebClientResponseException e) {
            System.err.println("🚨 Error en la petición a Keycloak: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (Exception e) {
            System.err.println("🚨 Error inesperado al obtener el token: " + e.getMessage());
        }
    }
}
