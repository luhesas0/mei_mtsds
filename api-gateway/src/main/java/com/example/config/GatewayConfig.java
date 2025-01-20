package com.example.config;

import com.example.filters.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                // Rota para autenticação
                // Rota para gestao_utilizadores
                .route("gestao-utilizadores-route",r->r.path("/utilizadores/**")
                        .filters(f-> f
                                .filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                        .uri("lb://gestao_utilizadores")) //Nome registado no Eureka
                // Rota para criacao_menu
                //.route("menu-route",r->r.path("/menu/**")
                //        .filters(f-> f
                //                .filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                //        .uri("lb://criacao_menu")) //Nome registado no Eureka
                // Rota para verifica_stock
                //.route("stock-route",r->r.path("/stock/**")
                //        .filters(f-> f
                //                .filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                //        .uri("lb://verifica_stock")) //Nome registado no Eureka
                // Rota para repositorio_entregas
                //.route("repositorio-route",r->r.path("/repositorio/**")
                //        .filters(f-> f
                //                .filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                //        .uri("lb://repositorio_entregas"))
                // Rota para calculo-rota
                //.route("rotas-route",r->r.path("/rotas/**")
                //        .filters(f-> f
                //                .filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                //        .uri("lb://calculo-rota"))
                // Rota para gestao_notificações
                //.route("gestao-notificacoes-route", r -> r.path("/notificacoes/**)
                //                .filters(f-> f
                //                        .filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
                //                .uri("lb://gestao_notificacoes")) //Nome registado no Eureka
                .build();
    }
}
