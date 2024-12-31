package org.example.repository;

import org.example.models.RegistoLogins;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistoLoginsRepository extends JpaRepository<RegistoLogins, Long> {
    //Métodos especificos para registo de logins podem ser adicionados aqui
}
