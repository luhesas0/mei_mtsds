package org.example.repository;

import org.example.models.UtilizadoresRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilizadoresRolesRepository extends JpaRepository<UtilizadoresRoles, Long> {
    //Métodos específicos podem ser adicionados conforme necessidade
}
