package org.example.filters;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class RouteValidatorTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testOpenRoute(){
        //Testa uma rota aberta (não protegida)
        webTestClient.get()
                .uri("/auth/login")
                .exchange()
                .expectStatus().isOk(); //Valida que a rota aberta é acessível sem autenticação
    }

    @Test
    void testProtectedRouteWithoutToken(){
        //Testa uma rota protegida sem fornecer um token
        webTestClient.get()
                .uri("/utilizadores")
                .exchange()
                .expectStatus().isUnauthorized(); //Valida que a rota protegida retorna 401 sem token
    }

    @Test
    void testProtectedRouteWithInvalidToken(){
        //Testa uma rota proetegida com token inválido
        webTestClient.get()
                .uri("/utilizadores")
                .header("Authorization","Bearer invalid-token")
                .exchange()
                .expectStatus().isUnauthorized(); //Valida que a rota protegida retorna 401 com token inválido
    }

    @Test
    void testProtectedRouteWithValidToken(){
        //Simula a validação de uma rota protegida com um token válido
        webTestClient.get()
                .uri("/utilizadores")
                .header("Authorization", "Bearer valid-token")
                .exchange()
                .expectStatus().isOk(); //Valida que a rota protegida é acessível com token válido
    }
}
