package com.securityChat.boot.model;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.secure-chat")
public class OAuth2ClientProperties {

    private String clientId;
    private String clientSecret;

}
