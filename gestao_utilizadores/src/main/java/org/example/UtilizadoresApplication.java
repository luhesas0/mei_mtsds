package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Hello World
 *
 */

@SpringBootApplication
@EnableJpaAuditing
public class UtilizadoresApplication {
    public static void main(String[] args){
        SpringApplication.run(UtilizadoresApplication.class, args);

        Logger logger = LoggerFactory.getLogger(UtilizadoresApplication.class);
        logger.info("Hello World! A aplicação foi iniciada com sucesso.");
    }
}
