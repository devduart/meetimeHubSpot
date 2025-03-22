package com.wallydev.meettimehubspot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class HubspotServiceTest {

    @InjectMocks
    private HubspotService hubspotService;

    @BeforeEach
    void setUp() {
        hubspotService = new HubspotService(
                "test-client-id",
                "test-client-secret",
                "http://localhost:8080/hubspot/callback"
        );
    }

    @Test
    void shouldBuildAuthorizationUrl() {
        String url = hubspotService.buildAuthorizationUrl();
        assertNotNull(url);
        assertTrue(url.contains("client_id=test-client-id"));
        assertTrue(url.contains("redirect_uri=http://localhost:8080/hubspot/callback"));
        assertTrue(url.contains("scope="));
        assertTrue(url.contains("response_type=code"));
    }
}
