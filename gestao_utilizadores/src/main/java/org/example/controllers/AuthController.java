package org.example.controllers;

import jakarta.validation.Valid;
import org.example.dto.*;
import org.example.models.Utilizador;
import org.example.service.AuthService;
import org.example.service.TokenService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    private final ModelMapper modelMapper;

    public AuthController(){
        this.modelMapper = new ModelMapper();
    }

    /**
     * Login de utilizador e gerar token JWT.
     * @param loginDto Dados de Login (email e password).
     * @return Token JWT ou erro de autenticação.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto loginDto){
        logger.info("Pedido de Login para utilizador:{}", loginDto.getEmail());
        try {
            var authToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
            var authResult = authenticationManager.authenticate(authToken);

            String token = tokenService.generateToken((Utilizador) authResult.getPrincipal());

            LoginResponseDto response = new LoginResponseDto();
            response.setToken(token);

            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("Falha no Login do utilizador {}:{}",loginDto.getEmail(),e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        }
    }

    /**
     * Registar um novo utilizador
     * @param criarUtilizadorDto Dados do utilizador a ser criado.
     * @return Dados do utilizador criado ou erro.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid CriarUtilizadorDto criarUtilizadorDto){
        logger.info("Pedido de registo do utilizador:{}", criarUtilizadorDto.getEmail());
        try {
            UtilizadorDto utilizador = authService.registerUser(criarUtilizadorDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(utilizador);
        } catch (IllegalArgumentException e){
            logger.error("Erro ao registar utilizador {}: {}", criarUtilizadorDto.getEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Validar um token JWT.
     * @param token Token JWT enviado pelo cliente.
     * @return Indicação se o token é válido.
     */
    @GetMapping("/validate")
    public ResponseEntity<?> validate(@RequestParam String token){
        logger.info("Pedido de validação de token.");
        try{
           String userEmail = tokenService.validateToken(token);
           return ResponseEntity.ok("Token válido para o utilizador:"+ userEmail);
        } catch (Exception e) {
            logger.error("Erro ao validar token: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("O Token é inválido ou expirou.");
        }
    }

    /**
     * Atualizar um token JWT próximo de expirar.
     * @param token Token JWT atual.
     * @return Novo token JWT ou erro de autorização.
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestParam String token){
        logger.info("Pedido de atualização de token.");
        try{
            String newToken = tokenService.refreshToken(token);
            LoginResponseDto response = new LoginResponseDto();
            response.setToken(newToken);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            logger.error("Erro ao atualizar o token:{}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autalização do token.");
        }
    }

    /**
     * Qualquer endpoint não autenticado é bloqueado por padrão.
     */
    @RequestMapping("*")
    public ResponseEntity<?> unauthorizedAccess(){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
    }
}
