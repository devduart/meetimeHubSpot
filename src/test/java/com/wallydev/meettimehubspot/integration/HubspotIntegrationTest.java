package com.wallydev.meettimehubspot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallydev.meettimehubspot.dto.ContactRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "HUBSPOT_CLIENT_ID=test-client-id",
        "HUBSPOT_CLIENT_SECRET=test-client-secret",
        "spring.security.oauth2.client.registration.hubspot.redirect-uri=http://localhost:8080/hubspot/callback"
})
public class HubspotIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateContactEndpointIntegration() throws Exception {

        var contactDto = new ContactRequestDto("Integration", "Test", "integration@test.com");


        mockMvc.perform(post("/hubspot/create-contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactDto)))
                .andExpect(status().is5xxServerError());
    }
}