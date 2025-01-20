package com.example.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidade que representa os papéis (roles) de utilizadores no sistema.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "role")
public class Papel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id; //Identificador único

    @Column(name = "nome", nullable = false, length = 50)
    private String nome; //Nome do papel (Admin, Funcionário, Cliente)

    @Column(name= "descricao",nullable = true,length = 255)
    private String descricao; //Descrição opcional do papel

    @CreatedDate
    @Column(name = "data_criacao", nullable = false,updatable = false)
    private LocalDateTime dataCriacao; //Data de criação

    @LastModifiedDate
    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao; //Data da última modificação

    @ManyToMany
    @JoinTable(
            name = "papel_permissoes",
            joinColumns = @JoinColumn(name = "papel_id"),
            inverseJoinColumns = @JoinColumn(name = "permissao_id")
    )
    private List<Permissao> permissoes; //Lista de permissões associadas ao papel
}
