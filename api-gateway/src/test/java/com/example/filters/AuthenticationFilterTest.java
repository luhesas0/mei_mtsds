package com.example.filters;

import com.example.config.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthenticationFilterTest {

    private AuthenticationFilter authenticationFilter;
    private RouteValidator routeValidator;
    private RestTemplate restTemplate;
    private AppConfig appConfig;

    @BeforeEach
    void setUp(){
        // Mockar as dependências
        routeValidator = mock(RouteValidator.class);
        restTemplate = mock(RestTemplate.class);
        appConfig = mock(AppConfig.class);

        // Inicializar AuthenticationFilter
        authenticationFilter = new AuthenticationFilter(routeValidator, restTemplate, appConfig);

        when(routeValidator.isSecured).thenReturn(request -> true);
    }

    @Test
    void shouldAllowAccessToOpenApiRoutes(){
        // Simular uma rota pública
        when(routeValidator.isSecured.test(any(ServerHttpRequest.class))).thenReturn(false);

        // Mockar ServerWebExchange e configurar o request
        ServerWebExchange exchange = mock(ServerWebExchange.class);
        ServerHttpRequest request = MockServerHttpRequest.get("/auth/login").build();
        when(exchange.getRequest()).thenReturn(request);

        // Mockar o GatewayFilterChain
        GatewayFilterChain chain = mock(GatewayFilterChain.class);
        when(chain.filter(exchange)).thenReturn(Mono.empty());

        // Executar o filtro
        authenticationFilter.apply(new AuthenticationFilter.Config()).filter(exchange, chain);

        // Verificar que o filtro continuou o processamento
        verify(chain, times(1)).filter(exchange);
    }

   @Test
    void shouldBlockUnauthorizedAccessIfNoAuthHeader(){
        // Configurar o comportamento para uma rota protegida
       when(routeValidator.isSecured.test(any(ServerHttpRequest.class))).thenReturn(true);

       // Mockar serverWebExchange e configurar o request
       ServerWebExchange exchange = mock(ServerWebExchange.class);
       ServerHttpRequest request = MockServerHttpRequest.get("/protected/resource").build();
       when(exchange.getRequest()).thenReturn(request);

       // Mockar o GatewayFilterChain
       GatewayFilterChain chain = mock(GatewayFilterChain.class);

       // Executar o filtro
       authenticationFilter.apply(new AuthenticationFilter.Config()).filter(exchange, chain);

       // Verificar que o filtro não continuou o processamento
       verify(chain, never()).filter(exchange);
   }

   @Test
    void shouldAddAuthorizationHeaderForValidToken(){
       // Configurar o comportamento para uma rota protegida
       when(routeValidator.isSecured.test(any(ServerHttpRequest.class))).thenReturn(true);
       when(appConfig.getUrl()).thenReturn("http://localhost:8080");

       // Criar um request com o header Authorization
       ServerWebExchange exchange = mock(ServerWebExchange.class);
       ServerHttpRequest request = MockServerHttpRequest.get("/protected/resource")
               .header(HttpHeaders.AUTHORIZATION, "Bearer valid-token")
               .build();
       when(exchange.getRequest()).thenReturn(request);

       // Configurar o RestTemplate para retornar resposta válida
        when(restTemplate.getForEntity(anyString(),eq(String.class))).thenReturn(ResponseEntity.ok("Valid"));

       // Mockar o GatewayFilterChain
       GatewayFilterChain chain = mock(GatewayFilterChain.class);
       when(chain.filter(exchange)).thenReturn(Mono.empty());

       // Executar o filtro
       authenticationFilter.apply(new AuthenticationFilter.Config()).filter(exchange, chain);

       // Verificar que o filtro continuou o processamento
       verify(chain, times(1)).filter(exchange);
   }
}
