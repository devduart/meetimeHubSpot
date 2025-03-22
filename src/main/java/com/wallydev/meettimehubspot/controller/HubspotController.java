package com.wallydev.meettimehubspot.controller;


import com.wallydev.meettimehubspot.dto.ContactRequestDto;
import com.wallydev.meettimehubspot.service.HubspotService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

@RestController
@RequestMapping("/hubspot")
@Tag(name = "Hubspot Integration API", description = "Endpoints para integração com HubSpot")
public class HubspotController {

    private static final Logger logger = LoggerFactory.getLogger(HubspotController.class);
    private final HubspotService hubspotService;

    public HubspotController(HubspotService hubspotService) {
        this.hubspotService = hubspotService;
    }

    @Operation(summary = "Gerar URL de autorização OAuth", description = "Retorna a URL para autenticação do usuário no HubSpot")
    @GetMapping("/authorize-url")
    public ResponseEntity<String> getAuthorizationUrl() {
        return ResponseEntity.ok(hubspotService.buildAuthorizationUrl());
    }

    @Operation(summary = "Processar callback OAuth", description = "Recebe o código de autorização do HubSpot e troca por access token")
    @GetMapping("/callback")
    public ResponseEntity<Map<String, Object>> handleOAuthCallback(@RequestParam("code") String code) {
        return hubspotService.exchangeCodeForToken(code)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.error("Falha ao trocar código por token.");
                    return ResponseEntity.internalServerError().build();
                });
    }

    @Operation(summary = "Criar contato no HubSpot", description = "Cria um novo contato na base do HubSpot usando o access token obtido")
    @PostMapping("/create-contact")
    public ResponseEntity<Map<String, Object>> createContact(@RequestBody ContactRequestDto contactDto) {
        Map<String, Object> contactData = Map.of(
                "properties", Map.of(
                        "firstname", contactDto.firstname(),
                        "lastname", contactDto.lastname(),
                        "email", contactDto.email()
                )
        );

        return hubspotService.createContact(contactData)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }

    @Operation(summary = "Receber webhook", description = "Endpoint para receber notificações de eventos do HubSpot")
    @PostMapping("/webhook")
    public ResponseEntity<String> receiveWebhook(@RequestBody Map<String, Object> webhookEvent) {
        logger.info("Webhook recebido: {}", webhookEvent);
        hubspotService.processWebhook(webhookEvent);
        return ResponseEntity.ok("Webhook processado com sucesso.");
    }

}
