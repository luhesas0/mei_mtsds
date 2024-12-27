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
                .route("utilizadores-route",r->r.path("/utilizadores/").uri("http://localhost:8071"))
                .route("menu-route",r->r.path("/menu/**").uri("http://localhost:8072"))
                .route("stock-route",r->r.path("/stock/**").uri("http://localhost:8073"))
                .route("repositorio-route",r->r.path("/repositorio/**").uri("http://localhost:8074"))
                .route("rotas-route",r->r.path("/rotas/**").uri("http://localhost:8075"))
                .build();
    }
}
