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
                .route("criacao_menu",r->r.path("/menu/**").uri("http://localhost:8071"))
                .route("verifica_stock",r->r.path("/stock/**").uri("http://localhost:8072"))
                .route("repositorio_entregas",r->r.path("/entregas/**").uri("http://localhost:8073"))
                .route("calculo_rotas",r->r.path("/rotas/**").uri("http://localhost:8074"))
                .route("gestao_utilizadores",r->r.path("/utilizadores/").uri("http://localhost:8070"))
                .build();
    }
}
