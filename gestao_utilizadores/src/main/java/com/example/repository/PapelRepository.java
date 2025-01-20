package com.example.repository;

import com.example.models.Papel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para a entidade Papel (role).
 */
public interface PapelRepository extends JpaRepository<Papel, String> {
}
