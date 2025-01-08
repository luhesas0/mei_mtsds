package org.example.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.example.models.Utilizador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Serviço responsável por gerar e validar tokens JWT.
 */
@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * Define o segredo para ambientes de teste
     *
     * @param secret Segredo do token.
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * Gera um token JWT para o utilizador.
     *
     * @param utilizador Utilizador autenticado.
     * @return Token JWT.
     * @throws IllegalArgumentException se o segredo não estiver configurado.
     */
    public String generateToken(Utilizador utilizador) {
        logger.info("Iniciando geração de token para o utilizador: {}",utilizador.getEmail());

        if (secret == null || secret.isEmpty()) {
            logger.error("O segredo do token está ausente ou vazio.");
            throw new IllegalArgumentException("O segredo do token não pode ser nulo ou vazio.");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-service")
                    .withSubject(utilizador.getEmail())
                    .withClaim("id", utilizador.getId())
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 7200000)) //2 horas de validade
                    .sign(algorithm);

            logger.info("Token gerado com sucesso para o utilizador:{}",utilizador.getEmail());
            return token;
        } catch (JWTCreationException e) {
            logger.error("Erro ao gerar o token JWT para o utilizador:{}",utilizador.getEmail(),e);
            throw new RuntimeException("Erro ao gerar token JWT.", e);
        }
    }

    /**
     * Valida um token JWT.
     *
     * @param token Token JWT.
     * @return E-mail do utilizador, se o token for válido.
     * @throws RuntimeException se o token for inválido.
     */
    public String validateToken(String token){
        logger.info("Iniciando validação do token.");

        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String subject = JWT.require(algorithm)
                    .withIssuer("auth-service")
                    .build()
                    .verify(token)
                    .getSubject();

            logger.info("Token válido para o utilizador:{}", subject);
            return subject;
        } catch (JWTVerificationException e){
            logger.error("Token inválido.",e);
            throw new RuntimeException("Token inválido.", e);
        }
    }

    /**
     * Renova um token JWT válido.
     *
     * @param token Token JWT atual.
     * @return Novo token JWT.
     */
    public String refreshToken(String token){
        logger.info("Renovando token.");

        String userEmail = validateToken(token);
        Utilizador fakeUser = new Utilizador();
        fakeUser.setEmail(userEmail);

        logger.info("Token renovado para o utilizador:{}",userEmail);
        return generateToken(fakeUser);
    }
}
