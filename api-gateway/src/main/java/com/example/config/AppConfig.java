package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "auth.service")
public class AppConfig {
    private String url;

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }
}
