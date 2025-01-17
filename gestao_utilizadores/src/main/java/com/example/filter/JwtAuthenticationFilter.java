package com.example.filter;

import com.example.service.TokenService;
import com.example.service.UtilizadorService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro para autenticação de requisições usando JWT.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UtilizadorService utilizadorService;

    public JwtAuthenticationFilter(TokenService tokenService, UtilizadorService utilizadorService){
        this.tokenService = tokenService;
        this.utilizadorService = utilizadorService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException{
        String authHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String userEmail = null;

        // Extrai o token do cabeçalho Authorization
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            jwtToken = authHeader.substring(7); //Remove o prefixo "Bearer"
            userEmail = tokenService.getEmailFromToken(jwtToken); //Decodifica o token para obter o email
        }

        // Valida o token e autentica o utilizador
        if(userEmail !=null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = utilizadorService.loadUserByUsername(userEmail);

            if(tokenService.isTokenValid(jwtToken, userDetails)){
                var authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
