package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class RouteCalculator
{
    public static void main( String[] args )
    {
        SpringApplication.run(RouteCalculator.class, args);

        Logger logger = LoggerFactory.getLogger(RouteCalculator.class);
        logger.info("MS_Route_Calculator iniciado!");
    }
}
