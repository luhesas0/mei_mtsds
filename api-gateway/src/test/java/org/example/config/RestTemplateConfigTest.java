package org.example.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RestTemplateConfigTest {

    @Test
    void testRestTemplateBeanCreation(){
        RestTemplateConfig restTemplateConfig = new RestTemplateConfig();
        RestTemplate restTemplate = restTemplateConfig.restTemplate();

        assertNotNull(restTemplate, "O RestTemplate não dever ser nulo.");
    }
}
