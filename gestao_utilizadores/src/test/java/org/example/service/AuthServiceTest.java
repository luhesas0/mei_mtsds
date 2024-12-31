package org.example.service;

import org.example.dto.LoginDto;
import org.example.dto.LoginResponseDto;
import org.example.models.Utilizador;
import org.example.repository.UtilizadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UtilizadorRepository utilizadorRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAutenticar_Success(){
        //Configuração do utilizador simulado
        Utilizador utilizador = new Utilizador();
        utilizador.setId(1L);
        utilizador.setEmail("test@example.com");
        utilizador.setPassword("hashed_password");

        // Dados de login simulados
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@example.com");
        loginDto.setPassword("plain_password");

        //Configurar mocks
        when(utilizadorRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(utilizador));
        when(passwordEncoder.matches("plain_password", "hashed_password"))
                .thenReturn(true);
        when(tokenService.generateToken(utilizador))
                .thenReturn("mocked_token");

        //Executar método
        LoginResponseDto response = authService.autenticar(loginDto);

        //Verificar resultados
        assertNotNull(response);
        assertEquals("mocked_token", response.getToken());
        verify(utilizadorRepository, times(1)).findByEmail("test@example.com");
        verify(passwordEncoder, times(1)).matches("plain_password", "hashed_password");
        verify(tokenService, times(1)).generateToken(utilizador);
    }

    @Test
    void tesAutenticar_InvalidEmail(){
        //Dados de login simulados
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("invalid@example.com");
        loginDto.setPassword("plain_password");

        //Configurar mocks
        when(utilizadorRepository.findByEmail("invalid@example.com"))
                .thenReturn(Optional.empty());

        //Verificar exceção
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                authService.autenticar(loginDto)
        );

        assertEquals("Email ou password inválidos.", exception.getMessage());
        verify(utilizadorRepository, times(1)).findByEmail("invalid@example.com");
        verifyNoInteractions(passwordEncoder);
        verifyNoInteractions(tokenService);
    }

    @Test
    void testAutenticar_InvalidPassword(){
        //Configuração do utilizador simulado
        Utilizador utilizador = new Utilizador();
        utilizador.setId(1L);
        utilizador.setEmail("test@example.com");
        utilizador.setPassword("hashed_password");


        //Dados de login simulados
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@example.com");
        loginDto.setPassword("wrong_password");

        //Configurar mocks
        when(utilizadorRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(utilizador));
        when(passwordEncoder.matches("wrong_password", "hashed_password"))
                .thenReturn(false);

        //Verificar exceção
        Exception exception = assertThrows(IllegalArgumentException.class, ()->
                authService.autenticar(loginDto)
        );

        assertEquals("Email ou password inválidos.", exception.getMessage());
        verify(utilizadorRepository, times(1)).findByEmail("test@example.com");
        verify(passwordEncoder, times(1)).matches("wrong_password", "hashed_password");
        verifyNoInteractions(tokenService);
    }
}
