package org.example.repository;

import org.example.models.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UtilizadorRepository extends JpaRepository<Utilizador, Long> {
    /**
     * Busca um utilizador pelo email.
     *
     * @param email o email do utilizador.
     * @return Um objeto UserDetails para autenticação no Spring Security.
     */
    Optional<UserDetails> findByEmail(String email);

    /**
     * Verifica se um utilizador existe pelo email.
     *
     * @param email o email do utilizador.
     * @return true se o utilizador existe, caso contrário false.
     */
    boolean existsByEmail(String email);
}
