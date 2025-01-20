package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiGatewayApplicationTest {

    @Test
    void contextLoads(){
        System.out.println("Contexto carregado com Sucesso!");
    }

    @Test
    void applicationStarts(){
        ApiGatewayApplication.main(new String[]{});
        System.out.println("Aplicação iniciou com sucesso!");
    }
}
