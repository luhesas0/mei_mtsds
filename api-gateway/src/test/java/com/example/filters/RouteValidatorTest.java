package com.example.filters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class RouteValidatorTest {

    private RouteValidator routeValidator;

    @BeforeEach
    void setUp(){
        routeValidator = new RouteValidator();
    }

    @Test
    void testOpenApiEndpointIsNotSecured(){
        //Simular um endpoint público real
        ServerHttpRequest request = MockServerHttpRequest.get("/auth/login").build();

        // Validar que a rota não é protegida
        boolean isSecured = routeValidator.isSecured.test(request);
        assertThat(isSecured).isFalse();
        System.out.println("A rota '/auth/login' foi corretamente identificada como pública.");
    }

    @Test
    void testProtectedEndpointIsSecured(){
        //Simular um endpoint protegido
        ServerHttpRequest request = MockServerHttpRequest.get("/utilizadores/create").build();

        // Validar que a rota é protegida
        boolean isSecured = routeValidator.isSecured.test(request);
        assertThat(isSecured).isTrue();
        System.out.println("A rota 'utilizadores/create/' foi corretamente identificada como protegida.");
    }

    @Test
    void testPartialMatchWithPublicEndpoint(){
        //Simular uma rota parcialmente semelhante a um endpoint público
        ServerHttpRequest request = MockServerHttpRequest.get("/auth/login/extra").build();

        //Validar que a rota é protegida (não corresponde exatamente a nenhum endpoint público)
        boolean isSecured = routeValidator.isSecured.test(request);
        assertThat(isSecured).isTrue();
        System.out.println("A rota '/auth/login/extra' foi corretamente identificada como protegida.");
    }
}
