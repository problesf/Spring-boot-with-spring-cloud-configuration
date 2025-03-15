package com.securityChat.boot.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

@Service
public class OAuth2TokenService {

	@Autowired
    private OAuth2AuthorizedClientService authorizedClientService;
	
	@Autowired
    private ClientRegistrationRepository clientRegistrationRepository;


    public OAuth2AccessToken getAccessToken() {
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                "secure-chat", "system"
        );

        return authorizedClient != null ? authorizedClient.getAccessToken() : null;
    }
}
