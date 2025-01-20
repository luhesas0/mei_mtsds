package com.example.config;

import com.example.filters.AuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GatewayRouteIntegrationTest {

    @Test
    void testCustomRouteLocator(){
        //Mock do AuthenticationFilter
        AuthenticationFilter mockAuthFilter = mock(AuthenticationFilter.class);

        // Mock do RouteLocatorBuilder
        RouteLocatorBuilder mockBuilder = mock(RouteLocatorBuilder.class);
        RouteLocatorBuilder.Builder mockRouteBuilder = mock(RouteLocatorBuilder.Builder.class);
        RouteLocator mockRouteLocator = mock(RouteLocator.class);

        // Configurar comportamento dos mocks
        when(mockBuilder.routes()).thenReturn(mockRouteBuilder);
        when(mockRouteBuilder.route(anyString(), any())).thenReturn(mockRouteBuilder);
        when(mockRouteBuilder.build()).thenReturn(mockRouteLocator);

        //Testar GatewayConfig
        GatewayConfig gatewayConfig = new GatewayConfig(mockAuthFilter);
        RouteLocator routeLocator = gatewayConfig.customRouteLocator(mockBuilder);

        // Validar que as rotas forma configuradas corretamente
        assertThat(routeLocator).isNotNull();
        System.out.println("Rotas configuradas corretamente!");
    }
}
