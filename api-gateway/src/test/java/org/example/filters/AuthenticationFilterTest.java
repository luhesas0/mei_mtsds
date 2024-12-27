package org.example.filters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class AuthenticationFilterTest {

    @Autowired

    private ApplicationContext context;
    private WebTestClient webTestClient;

    @BeforeEach
    void setup(){
        this.webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void testAccessToProtectedRouteWithValidToken(){
        webTestClient.get()
                .uri("/utilizadores")
                .header(HttpHeaders.AUTHORIZATION,"Bearer valid-token")
                .exchange()
                .expectStatus().isOk(); //Verifica que a rota é acessivel com token válido
    }

    @Test
    void testAccessToProtectedRouteWithoutToken(){
        webTestClient.get()
                .uri("/utilizadores/")
                .exchange()
                .expectStatus().isUnauthorized(); //Verifica que sem token a rota retorna 401
    }

    @Test
    void testAccessToOpenRoute(){
        webTestClient.get()
                .uri("/auth/login")
                .exchange()
                .expectStatus().isOk(); //Valida que as rotas abertas são acessíveis
    }
}
