package org.example.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.example.models.Utilizador;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    //Adicionando para permitir configuração do segredo de testes

    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * Gera um token JWT para o utilizador.
     * @param utilizador Utilizador autenticado.
     * @return Token JWT.
     */
    public String generateToken(Utilizador utilizador) {
        if (secret == null || secret.isEmpty()) {
            throw new IllegalArgumentException("O segredo do token não pode ser nulo ou vazio.");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-service")
                    .withSubject(utilizador.getEmail())
                    .withClaim("id", utilizador.getId())
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 7200000))
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token JWT.", e);
        }
    }

    /**
     * Valida um token JWT.
     * @param token Token JWT.
     * @return E-mail do utilizador, se o token for válido.
     */
    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-service")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e){
            throw new RuntimeException("Token inválido.", e);
        }
    }

    public String refreshToken(String token){
        String userEmail = validateToken(token);
        Utilizador fakeUser = new Utilizador();
        fakeUser.setEmail(userEmail);
        return generateToken(fakeUser);
    }
}
