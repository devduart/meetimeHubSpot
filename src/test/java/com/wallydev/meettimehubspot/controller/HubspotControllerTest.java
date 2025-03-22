package com.wallydev.meettimehubspot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallydev.meettimehubspot.dto.ContactRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "HUBSPOT_CLIENT_ID=test-client-id",
        "HUBSPOT_CLIENT_SECRET=test-client-secret",
        "spring.security.oauth2.client.registration.hubspot.redirect-uri=http://localhost:8080/hubspot/callback"
})
public class HubspotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAuthorizeUrlEndpoint() throws Exception {
        mockMvc.perform(get("/hubspot/authorize-url"))
                .andExpect(status().isOk());
    }

    @Test
    void testCallbackEndpointWithDummyCode() throws Exception {
        mockMvc.perform(get("/hubspot/callback?code=dummy-code"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void testCreateContactEndpointWithoutToken() throws Exception {
        var contactDto = new ContactRequestDto("John", "Doe", "john.doe@example.com");

        mockMvc.perform(post("/hubspot/create-contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactDto)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void testWebhookEndpoint() throws Exception {
        mockMvc.perform(post("/hubspot/webhook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"eventType\":\"contact.creation\"}"))
                .andExpect(status().isOk());
    }
}