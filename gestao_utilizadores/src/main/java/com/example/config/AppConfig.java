package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuração global para o RestTemplate.
 */
@Configuration
public class AppConfig {
    /**
     * Bean para RestTemplate.
     * O RestTemplate será usado para comunicação com outros microserviços.
     *
     * @return Instância configurada de RestTemplate.
     */
    @Bean
    public RestTemplate template(){
        return new RestTemplate();
    }
}
