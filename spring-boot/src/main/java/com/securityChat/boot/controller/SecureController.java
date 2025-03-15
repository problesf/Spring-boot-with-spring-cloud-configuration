package com.securityChat.boot.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.securityChat.boot.oauth.OAuth2TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@RequestMapping("/api")
public class SecureController {

	@Autowired
	OAuth2TokenService oAuth2TokenService;
	
    @GetMapping("/publico")
    public String publicEndpoint() {
        return "Este endpoint es público.";
    }

    @GetMapping("/privado")
    public String privateEndpoint(@AuthenticationPrincipal Jwt jwt) {
        return "Este endpoint está protegido. Usuario: " + jwt.getSubject();
    }
    
    @GetMapping("/token")
    public String getToken() {
        OAuth2AccessToken token = oAuth2TokenService.getAccessToken();
        return token != null ? token.getTokenValue() : "No token available";
    }
}
