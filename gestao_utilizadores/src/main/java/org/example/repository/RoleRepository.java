package org.example.repository;

import org.example.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Busca uma role pelo nome.
     *
     * @param nome o nome da role.
     * @return A role correspondente, se existir.
     */
    Optional<Role> findByNome(String nome);
}
