package com.example;

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
public class NotificacoesApplication {
    public static void main(String[] args){
        SpringApplication.run(NotificacoesApplication.class, args);

        Logger logger=
                LoggerFactory.getLogger(NotificacoesApplication.class);
        logger.info("Hello World! Aplicação foi iniciada com sucesso.");
    }
}
