package com.estg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class RepositorioEntregas
{
    public static void main( String[] args )
    {
        SpringApplication.run(RepositorioEntregas.class, args);

        Logger logger = LoggerFactory.getLogger(RepositorioEntregas.class);
        logger.info("MS_Repo_Entregas iniciado!");
    }
}
