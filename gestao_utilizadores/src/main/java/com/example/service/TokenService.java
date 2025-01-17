package com.example.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.models.Utilizador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Serviço para gestão de tokens JWT.
 */
@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Value("${api.security.token.secret}")
    private String secret;

    /**
     *  Gera um token JWT para o utilizador autenticado.
     *
     * @param utilizador Utilizador autenticado.
     * @return Token JWT gerado.
     */
    public String generateToken(Utilizador utilizador){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth")
                    .withSubject(utilizador.getEmail())
                    .withClaim("id", utilizador.getId())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e){
            logger.error("Erro ao gerar o token: {}", e.getMessage());
            throw new RuntimeException("Erro ao gerar o token.", e);
        }
    }

    /**
     * Atualiza um token JWT existente.
     *
     * @param token Token atual.
     * @return Novo token JWT gerado.
     */
    public String refreshToken(String token){
        try{
            String email = JWT.decode(token).getSubject();
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth")
                    .withSubject(email)
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (Exception e){
            logger.error("Erro ao atualizar o token:{}", e.getMessage());
            throw new RuntimeException("Erro ao atualizar o token.", e);
        }
    }

    /**
     * Valida um token JWT fornecido.
     *
     * @param token Token JWT para validação.
     * @return Resposta indicando se o token é válido.
     */
    public ResponseEntity<String> validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String subject = JWT.require(algorithm)
                    .withIssuer("auth")
                    .build()
                    .verify(token)
                    .getSubject();
            return ResponseEntity.status(HttpStatus.OK).body("Token válido para:" + subject);
    } catch (JWTVerificationException e){
            logger.error("Erro ao validar o token: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido.");
        }
    }

    /**
     * Gera a data de expiração para o token JWT.
     *
     * @return Data de expiração.
     */
    private Date genExpirationDate(){
        return new Date(System.currentTimeMillis() + 7200000); //2horas
    }

    /**
     * Extrai o email do utilizador a partir de um token JWT.
     *
     * @param token Token JWT.
     * @return Email do utilizador.
     */
    public String getEmailFromToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e){
            logger.error("Erro ao obter email do token: {}", e.getMessage());
            throw new RuntimeException("Erro ao obter email do token.", e);
        }
    }

    /**
     * Verifica se o token é válido para um utilizador.
     *
     * @param token Token JWT.
     * @param userDetails Informações do utilizador.
     * @return 'true' se o token for válido, caso contrário, 'false'.
     */
    public boolean isTokenValid(String token, UserDetails userDetails){
        String email = getEmailFromToken(token);
        return email != null && email.equals(userDetails.getUsername());
    }
}
