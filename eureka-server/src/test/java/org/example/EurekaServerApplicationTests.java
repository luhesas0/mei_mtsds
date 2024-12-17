package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EurekaServerApplicationTests {

    @Test
    void contextLoads(){
        // Este teste verifica se o contexto do Spring Boot é carregado corretamente.
    }

    @Test
    void applicationsStarts(){
        EurekaServerApplication.main(new String[]{});
        // Este teste valida que a aplicação inicializa sem erros.
    }
}
