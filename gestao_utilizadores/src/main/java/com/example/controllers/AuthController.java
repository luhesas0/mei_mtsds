package com.example.controllers;

import jakarta.validation.Valid;
import com.example.dto.AutenticacaoDTO;
import com.example.dto.UtilizadorDTO;
import com.example.exceptions.UtilizadorExistente;
import com.example.models.Utilizador;
import com.example.service.TokenService;
import com.example.service.UtilizadorService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para operações de autenticação e gestão de tokens.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UtilizadorService utilizadorService;

    @Autowired
    private TokenService tokenService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Autentica um utilizador e gera um token JWT.
     *
     * @param autenticacaoDTO Dados de autenticação (email e password):
     * @return Token JWT se a autenticação for bem sucedida.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AutenticacaoDTO autenticacaoDTO){
        logger.info("Tentativa de login para o utilizador: {}", autenticacaoDTO.getEmail());
        try{
            var authRequest = new UsernamePasswordAuthenticationToken(
                    autenticacaoDTO.getEmail(), autenticacaoDTO.getPassword());

            var authResult = authenticationManager.authenticate(authRequest);
            var utilizador = (Utilizador) authResult.getPrincipal();

            String token = tokenService.generateToken(utilizador);

            autenticacaoDTO.setToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(autenticacaoDTO);
        } catch (Exception e){
            logger.error("Erro ao autenticar utilizador: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        }
    }

    /**
     * Regista um novo utilizador no sistema.
     *
     * @param utilizadorDTO Dados do utilizador a ser registado.
     * @return Detallhes do utilizador registado.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UtilizadorDTO utilizadorDTO){
        logger.info("Tentativa de registo para o utilizador: {}", utilizadorDTO.getEmail());
        try{
            Utilizador utilizador = modelMapper.map(utilizadorDTO, Utilizador.class);
            utilizador = utilizadorService.createUtilizador(utilizador);
            return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(utilizador, UtilizadorDTO.class));

        } catch (UtilizadorExistente e){
            logger.error("Erro ao registar utilizador: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Atualiza um token JWT expirado ou prestes a expirar.
     *
     * @param autenticacaoDTO Token atual fornecido pelo utilizador.
     * @return Novo token JWT.
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody AutenticacaoDTO autenticacaoDTO){
        logger.info("Pedido de atualização de token.");
        try{
            String novoToken = tokenService.refreshToken(autenticacaoDTO.getToken());
            autenticacaoDTO.setToken(novoToken);
            return ResponseEntity.status(HttpStatus.OK).body(autenticacaoDTO);

        } catch (Exception e){
            logger.error("Erro ao atualizar token: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido ou expirado.");
        }
    }

    /**
     *  Valida um token JWT.
     *
     * @param token Token JWT fornecido pelo utilizador.
     * @return Validação do token.
     */
    @GetMapping("/validate")
    public ResponseEntity<?> validate(@RequestParam("token") String token){
        logger.info("Pedido de validação de token.");
        return tokenService.validateToken(token);
    }
}
