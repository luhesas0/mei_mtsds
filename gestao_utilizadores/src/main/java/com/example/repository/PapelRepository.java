package com.example.repository;

import com.example.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para a entidade Papel (role).
 */
public interface PapelRepository extends JpaRepository<Role, String> {
}
