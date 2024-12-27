package org.example.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class GatewayRouteIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testUtilizadoresRoute(){
        webTestClient.get()
                .uri("utilizadores")
                .exchange()
                .expectStatus().isOk(); //Valida que a rota está configurada corretamente
    }

    @Test
    void testRotasNaoExistentes(){
        webTestClient.get()
                .uri("/rota-nao-existente")
                .exchange()
                .expectStatus().isNotFound(); //Valida que rotas não configuradas retornam 404
    }
}
