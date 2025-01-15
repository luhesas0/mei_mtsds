package org.example.repository;

import org.example.models.Papel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório para a entidade Role.
 */
public interface RoleRepository extends JpaRepository<Papel, Long> {
    /**
     * Busca uma papel (role) pelo nome.
     *
     * @param nome Nome do papel (role).
     * @return Role encontrada.
     */
    Optional<Papel> findByNome(String nome);
}
