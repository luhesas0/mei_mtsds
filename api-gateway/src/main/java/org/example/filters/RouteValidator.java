package org.example.filters;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;


@Component
public class RouteValidator {

    public boolean isSecured(ServerHttpRequest request) {
        //Lista de rotas públicas
        String[] openApiEndpoints = {"/auth/register", "/auth/login"};
        return openApiEndpoints.length == 0 || request.getPath().toString().matches("^.*(" + String.join("|", openApiEndpoints) + ").*$");
    }
}
