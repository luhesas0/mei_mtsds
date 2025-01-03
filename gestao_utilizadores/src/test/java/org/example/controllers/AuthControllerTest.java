package org.example.controllers;

import org.example.dto.CriarUtilizadorDto;
import org.example.dto.LoginDto;
import org.example.dto.LoginResponseDto;
import org.example.dto.UtilizadorDto;
import org.example.models.Utilizador;
import org.example.service.AuthService;
import org.example.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AuthService authService;

    @Mock
    private TokenService tokenService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess(){
        //Organizar
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@example.com");
        loginDto.setPassword("password");

        Utilizador mockUser = new Utilizador();
        mockUser.setEmail("test@example.com");
        mockUser.setId(1L);

        when(authenticationManager.authenticate(any())).thenReturn(
                new UsernamePasswordAuthenticationToken(mockUser, "password")
        );

        when(tokenService.generateToken(mockUser)).thenReturn("token-simulado");

        //Atuar
        ResponseEntity<?> response = authController.login(loginDto);

        // Afirmar
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("token-simulado",((LoginResponseDto) response.getBody()).getToken());
        verify(authenticationManager, times(1)).authenticate(any());
        verify(tokenService, times(1)).generateToken(any());
    }

    @Test
    void testValidateTokenSucess(){
        //Organizar
        String token = "token-valido";
        when(tokenService.validateToken(token)).thenReturn("test@example.com");

        //Atuar
        ResponseEntity<?>response=authController.validate(token);

        //Afirmar
        assertEquals(200,response.getStatusCodeValue());
        assertEquals("Token válido para o utilizador:test@example.com", response.getBody());
        verify(tokenService, times(1)).validateToken(token);
    }

    @Test
    void testLoginFailure(){
        //Organizar
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("invalid@example.com");
        loginDto.setPassword("wrongpassword");

        when(authenticationManager.authenticate(any())).thenThrow(RuntimeException.class);

        //Atuar
        ResponseEntity<?> response = authController.login(loginDto);

        //Afirmar
        assertEquals(401, response.getStatusCodeValue());
        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    void testRegisterSuccess(){
        //Afirmar
        CriarUtilizadorDto criarUtilizadorDto = new CriarUtilizadorDto();
        criarUtilizadorDto.setEmail("newuser@example.com");
        criarUtilizadorDto.setNome("New User");
        criarUtilizadorDto.setPassword("password");

        UtilizadorDto utilizadorDto = new UtilizadorDto();
        utilizadorDto.setEmail(criarUtilizadorDto.getEmail());
        utilizadorDto.setNome(criarUtilizadorDto.getNome());
        when(authService.registerUser(criarUtilizadorDto)).thenReturn(utilizadorDto);

        // Atuar
        ResponseEntity<?> response = authController.register(criarUtilizadorDto);

        //Afirmar
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(utilizadorDto, response.getBody());
        verify(authService, times(1)).registerUser(criarUtilizadorDto);
    }

    @Test
    void testRegisterFailure(){
        //Afirmar
        CriarUtilizadorDto criarUtilizadorDto = new CriarUtilizadorDto();
        criarUtilizadorDto.setEmail("existinguser@example.com");
        criarUtilizadorDto.setNome("Utilizador existente");
        criarUtilizadorDto.setPassword("password");

        when(authService.registerUser(criarUtilizadorDto)).thenThrow(IllegalArgumentException.class);

        //Atuar
        ResponseEntity<?> response = authController.register(criarUtilizadorDto);

        //Afirmar
        assertEquals(400, response.getStatusCodeValue());
        verify(authService, times(1)).registerUser(criarUtilizadorDto);
    }

    @Test
    void testRefreshTokenSuccess(){
        //Afirmar
        String oldToken = "old-token";
        when(tokenService.refreshToken(oldToken)).thenReturn("novo-token");

        //Atuar
        ResponseEntity<?> response = authController.refresh(oldToken);

        //Afirmar
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("novo-token", ((LoginResponseDto) response.getBody()).getToken());
        verify(tokenService, times(1)).refreshToken(oldToken);
    }

    @Test
    void testRefreshTokenFailure(){
        //Organizar
        String oldToken = "token-invalido";
        when(tokenService.refreshToken(oldToken)).thenThrow(RuntimeException.class);

        //Atuar
        ResponseEntity<?> response = authController.refresh(oldToken);

        //Afirmar
        assertEquals(401, response.getStatusCodeValue());
        verify(tokenService, times(1)).refreshToken(oldToken);
    }
}
