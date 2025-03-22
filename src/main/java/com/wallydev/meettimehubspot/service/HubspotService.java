package com.wallydev.meettimehubspot.service;


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Optional;

@Service
public class HubspotService {
    private static final Logger logger = LoggerFactory.getLogger(HubspotService.class);

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private String accessToken;

    public HubspotService(
            @Value("${spring.security.oauth2.client.registration.hubspot.client-id}") String clientId,
            @Value("${spring.security.oauth2.client.registration.hubspot.client-secret}") String clientSecret,
            @Value("${spring.security.oauth2.client.registration.hubspot.redirect-uri}") String redirectUri
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }


     public String buildAuthorizationUrl() {
     return UriComponentsBuilder.fromHttpUrl("https://app.hubspot.com/oauth/authorize")
         .queryParam("client_id", clientId)
         .queryParam("redirect_uri", redirectUri)
         .queryParam("scope", "oauth crm.objects.contacts.read crm.objects.contacts.write")
         .queryParam("optional_scope", "crm.lists.read")
         .queryParam("response_type", "code")
         .build()
         .encode()
         .toUriString();
    }


    public Optional<Map<String, Object>> exchangeCodeForToken(String code) {
        var restTemplate = new RestTemplate();

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        var request = new HttpEntity<>(body, headers);

        try {
            var response = restTemplate.postForEntity("https://api.hubapi.com/oauth/v1/token", request, Map.class);
            this.accessToken = (String) response.getBody().get("access_token");
            logger.info("Access token armazenado com sucesso.");
            return Optional.of(response.getBody());
        } catch (Exception e) {
            logger.error("Erro ao trocar código por token: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Map<String, Object>> createContact(Map<String, Object> contactData) {
        if (accessToken == null) {
            logger.warn("Access token ausente.");
            return Optional.empty();
        }

        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        var request = new HttpEntity<>(contactData, headers);

        try {
            var response = restTemplate.postForEntity("https://api.hubapi.com/crm/v3/objects/contacts", request, Map.class);
            logger.info("Contato criado com sucesso.");
            return Optional.ofNullable(response.getBody());
        } catch (Exception e) {
            logger.error("Erro ao criar contato: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public void processWebhook(Map<String, Object> webhookEvent) {
        if ("contact.creation".equals(webhookEvent.get("eventType"))) {
            logger.info("Evento de criação de contato processado.");
        } else {
            logger.info("Webhook recebido sem evento de criação de contato.");
        }
    }
}
