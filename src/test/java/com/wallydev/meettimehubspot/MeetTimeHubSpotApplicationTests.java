package com.wallydev.meettimehubspot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.security.oauth2.client.registration.hubspot.client-id=test-client-id",
        "spring.security.oauth2.client.registration.hubspot.client-secret=test-client-secret",
        "spring.security.oauth2.client.registration.hubspot.redirect-uri=http://localhost:8080/hubspot/callback"
})
class MeetTimeHubSpotApplicationTests {

    @Test
    void contextLoads() {
    }

}
