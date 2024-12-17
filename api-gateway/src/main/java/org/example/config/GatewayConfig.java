package org.example.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("auth-service",r->r.path("/api/auth/**").uri("http://localhost:8070"))
                .route("menu-service",r->r.path("/menu/**").uri("http://localhost:8071"))
                .route("stock-service",r->r.path("/stock/**").uri("http://localhost:8072"))
                .route("repositorio-entregas",r->r.path("/entregas/**").uri("http://localhost:8073"))
                .route("calculo-rotas",r->r.path("/rotas/**").uri("http://localhost:8074"))
                .route("funcionarios",r->r.path("/funcionarios/**").uri("http://localhost:8075"))
                .build();
    }
}
