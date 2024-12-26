package org.example.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.example.filters.RouteValidator;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory <AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private RestTemplate restTemplate;

    public AuthenticationFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config){
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            //Verificar se a rota é aberta
            if(!routeValidator.isSecured.test(request)){
                return chain.filter(exchange); //Permitir acesso a rotas abertas
            }

            //Verificar se existe um header Authorization
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            //Processar token JWT
            if (authHeader != null && authHeader.startsWith("Bearer ")){
                authHeader = authHeader.substring(7); //Remover "Bearer" do token
            } else {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            try{
                //Enviar o token para o microserviço gestao_utilizadores para validação
                String validateUrl = "http://localhost:8071/api/auth/validate?token=" + authHeader;
                ResponseEntity<String> response = restTemplate.getForEntity(validateUrl, String.class);

                if (response.getStatusCode() == HttpStatus.OK){
                    //Adicionar informações do utilizador no cabeçalho da requisição
                    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                            .header("Authorization", "Bearer " + authHeader)
                            .build();
                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                } else {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            } catch (HttpClientErrorException e){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            } catch (Exception e){
                exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                return exchange.getResponse().setComplete();
            }
        };
    }

    public static class Config{
        //Adicionar configurações adicionais se necessário
    }
}
