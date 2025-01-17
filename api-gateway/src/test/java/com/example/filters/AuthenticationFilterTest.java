package com.example.filters;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class AuthenticationFilterTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void testAccesToProtectedRouteWithValidToken(){
        // Simula uma resposta bem-sucedida do serviço de validação de token
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(new ResponseEntity<>("Token válido", HttpStatus.OK));

        webTestClient.get()
                .uri("/utilizadores")
                .header(HttpHeaders.AUTHORIZATION, "Bearer valid-token")
                .exchange()
                .expectStatus().isOk(); // Verifica que a rota é acessível com token válido
    }

    @Test
    void testAccessToProtectedRouteWithoutToken(){
        webTestClient.get()
                .uri("/utilizadores")
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testAccessToOpenRoute(){
        webTestClient.get()
                .uri("/auth/login")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testAccessToProtectedRouteWithInvalidToken(){
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(new ResponseEntity<>("Token inválido", HttpStatus.UNAUTHORIZED));

        webTestClient.get()
                .uri("/utilizadores")
                .header(HttpHeaders.AUTHORIZATION, "Bearer invalid-token")
                .exchange()
                .expectStatus().isUnauthorized();
    }
}
