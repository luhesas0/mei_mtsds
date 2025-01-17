package com.example.repository;

import com.example.models.RegistoPermissao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para operações relacionadas à entidade RegistoPermissao.
 */
public interface RegistoPermissaoRepository extends JpaRepository<RegistoPermissao, Long> {
}
