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

    // Construtor para ser usado em testes
    public GatewayConfig(AuthenticationFilter authenticationFilter){
        this.authenticationFilter = authenticationFilter;
    }

    // Construtor padrão necessário para o Spring
    public GatewayConfig(){
    }
//
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
//        return builder.routes()
//                .route("utilizadores-route",r->r.path("/utilizadores/**")
//                        .filters(f-> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
//                        .uri("lb://utilizadores")) //Microserviço: Gestao de utilizadores - Nome registado no Eureka
//                .route("menu-route", r-> r.path("/menu/**")
//                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
//                        .uri("lb://criacao_menu")) //Microserviço: Criação de Menus - Nome registado no Eureka
//                .route("stock-route", r-> r.path("/stock/**")
//                        .filters(f-> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
//                        .uri("lb://verifica_stock"))  //Microserviço: Verificação de Stock - Nome registado no Eureka
//                .route("repositorio-route", r-> r.path("/repositorio/**")
//                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
//                        .uri("lb://repositorio_entregas"))   //Microserviço: Repositorio de Entregas
//                .route("rotas-route", r -> r.path("/rotas/**")
//                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
//                        .uri("lb://calculo_rota"))  //Microserviço: Cálculo de Rotas
//                .route("notificacao-route", r -> r.path("/notificacoes/**")
//                        .filters(f-> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config())))
//                        .uri("lb://notificacao"))  //Microserviço: Serviço de Notificacoes
//                .build();
//    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("utilizadores-route", r -> r.path("/utilizadores/**")
                        .uri("lb://UTILIZADORES")) // Microserviço: Gestao de utilizadores - Nome registado no Eureka
                .route("menu-route", r -> r.path("/menu/**")
                        .uri("lb://MENU-CRIA")) // Microserviço: Criação de Menus - Nome registado no Eureka
                .route("stock-route", r -> r.path("/stock/**")
                        .uri("lb://VERIFICA-STOCK")) // Microserviço: Verificação de Stock - Nome registado no Eureka
                .route("repositorio-route", r -> r.path("/repositorio/**")
                        .uri("lb://REPOSITORIO-ENTREGAS")) // Microserviço: Repositorio de Entregas
                .route("rotas-route", r -> r.path("/route/**")
                        .uri("lb://CALCULO-ROTAS")) // Microserviço: Cálculo de Rotas
                .route("notificacao-route", r -> r.path("/notificacoes/**")
                        .uri("lb://NOTIFICACAO")) // Microserviço: Serviço de Notificacoes
                .build();
    }
}
