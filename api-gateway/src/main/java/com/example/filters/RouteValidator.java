package com.example.filters;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;


@Component
public class RouteValidator {

    //Lista de endpoints públicos
    public static final List<String> openApiEndpoints = Arrays.asList(
            "/auth/register",  //Registo de novos utilizadores
            "/auth/login",     // Login de utilizadores
            "/eureka",         // Endpoint do Eureka Server
            "/utilizadores/landing",
            "/rotas/"
            //Verificação do serviço de utilizadores
            );

    //Predicate para verificar se uma rota é protegida
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri->request.getURI().getPath().equals(uri));
}
