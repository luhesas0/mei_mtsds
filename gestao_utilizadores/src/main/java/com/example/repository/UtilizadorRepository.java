package com.example.repository;

import com.example.models.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Repositório para a operações relacionadas à entidade Utilizador.
 * Este repositório suporta operações CRUD e consultas personalizadas.
 */
public interface UtilizadorRepository extends JpaRepository<Utilizador, String> {
    /**
     * Busca os detalhes de um utilizador pelo email.
     *
     * @param email o email do utilizador.
     * @return um objeto UserDetails para autenticação no Spring Security.
     */
    UserDetails findByEmail(String email);

    /**
     * Verifica se um utilizador existe pelo email.
     *
     * @param email o email do utilizador.
     * @return true se o email existir, caso contrário false.
     */
    boolean existsByEmail(String email);
}
