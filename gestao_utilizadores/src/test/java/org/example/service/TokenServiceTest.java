package org.example.service;

import org.junit.jupiter.api.Disabled;
import org.example.models.Utilizador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

public class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        tokenService = new TokenService();
        //Configurar manualmente o valor do segredo
        tokenService.setSecret("Uw9cRedxi/J9GaDDACOseoeWUlIbrIVH8br/VzV29hc=");
    }

    @Test
    @Disabled("Desativado temporariamente para correção do caso de erro")
    void testGenerateToken_Success(){
        // Configuração do utilizador simulado
        Utilizador utilizador = new Utilizador();
        utilizador.setId(1L);
        utilizador.setEmail("test@example.com");

        // Execução do método a ser testado
        String token = tokenService.generateToken(utilizador);

        //Verificações
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testGenerateToken_Error(){
        //Configuração do utilizador simulado com dados incompletos
        Utilizador utilizador = new Utilizador(); //Dados incompletos

        //Verificação de exceção ao gerar o token
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                tokenService.generateToken(utilizador)
        );

        assertEquals("Erro ao gerar token JWT.",exception.getMessage());
    }

    @Test
    void testValidateToken_Success(){
        //Configuração do utilizador simulado
        Utilizador utilizador = new Utilizador();
        utilizador.setId(1L);
        utilizador.setEmail("test@example.com");

        //Gerar um token válido
        String token = tokenService.generateToken(utilizador);

        //Execução do método a ser testado
        String email = tokenService.validateToken(token);

        //Verificações
        assertNotNull(email);
        assertEquals("test@example.com", email);
    }

    @Test
    void testValidateToken_InvalidToken(){
        //Configuração de um token inválido
        String invalidToken = "invalid.token";

        //Verificação de exceção ao validar o token
        RuntimeException exception = assertThrows(RuntimeException.class,()->
                tokenService.validateToken(invalidToken)
        );

        assertTrue(exception.getMessage().contains("Token inválido"));
    }
}
