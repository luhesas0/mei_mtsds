package org.example.repository;

import org.example.models.Notificacoes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacoesRepository extends JpaRepository<Notificacoes, Long>{
    //Métodos específicos para gestão de notificações podem ser adicionados aqui
}
