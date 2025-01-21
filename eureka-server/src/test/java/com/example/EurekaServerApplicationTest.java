package com.example;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class EurekaServerApplicationTest {

    @Test
    void applicationStartsSuccessfully(){
        // Testa se a aplicação inicia sem lançar exceções
        try (MockedStatic<SpringApplication> springApplicationMock = mockStatic(SpringApplication.class)){
           assertDoesNotThrow(() -> {
               SpringApplication.run(EurekaServerApplication.class, new String[]{});
           });
           springApplicationMock.verify(() ->
                   SpringApplication.run(EurekaServerApplication.class, new String[]{}));
        }
    }

    @Test
    void mainMethodRunSuccessfully(){
        //Testa se o método main executa sem lançar exceções
        assertDoesNotThrow(()-> EurekaServerApplication.main(new String[]{}));
    }
}
