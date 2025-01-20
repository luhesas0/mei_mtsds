package com.example.service;

import com.example.models.Utilizador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.service.TokenService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TokenServiceTest {

    private TokenService tokenService;

    @BeforeEach
    void setup(){
        tokenService = new TokenService();
        tokenService.setSecret("${api.security.token.secret}");
    }

    @Test
    void testGenerateToken(){
        // Mock do objeto Utilizador
        Utilizador utilizador = new Utilizador();
        utilizador.setId(1L);
        utilizador.setEmail("test@example.com");

        // Gera o token
        String token = tokenService.generateToken(utilizador);

        // Verifica se o token não é nulo
        assertNotNull(token, "O token gerado não deve ser nulo");
        System.out.println("Token gerado com Sucesso:" + token);
    }
}
