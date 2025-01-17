package com.example.repository;

import com.example.models.RegistoLogin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para a entidade RegistoLogin.
 */

public interface RegistoLoginRepository extends JpaRepository<RegistoLogin, Long> {
    //Métodos especificos para registo de logins podem ser adicionados aqui
}
