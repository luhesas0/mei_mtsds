package org.example.config;

import org.junit.jupiter.api.Test;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GatewayConfigTest {

    @Test
    void testRouteLocatorCreation(){
        GatewayConfig gatewayConfig = new GatewayConfig();
        RouteLocatorBuilder builder = new RouteLocatorBuilder(null);
        RouteLocator routeLocator = gatewayConfig.customRouteLocator(builder);

        assertNotNull(routeLocator, "O RouteLocator não deve ser nulo.");
    }
}
