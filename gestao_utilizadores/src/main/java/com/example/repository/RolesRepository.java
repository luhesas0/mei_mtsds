package com.example.repository;

import com.example.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para a entidade Papel (role).
 */
public interface RolesRepository extends JpaRepository<Roles, String> {
}
