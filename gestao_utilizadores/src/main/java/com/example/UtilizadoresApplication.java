package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class UtilizadoresApplication {
    public static void main(String[] args){
        SpringApplication.run(UtilizadoresApplication.class,args);

        Logger logger = LoggerFactory.getLogger(UtilizadoresApplication.class);
        logger.info("Teste Logging");
    }
}
