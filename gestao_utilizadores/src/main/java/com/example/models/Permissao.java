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
 * Entidade que representa permissões no sistema.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "permissao")
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id; //Identificador único da permissão

    @Column(name = "nome", nullable = false, length = 100)
    private String nome; //Nome da permissão (Administrador=Acesso Total)

    @Column(name = "descricao", length = 255)
    private String descricao; //Descrição detalhada da permissão

    @CreatedDate
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao; //Data de criação da permissão (auditoria)

    @LastModifiedDate
    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao; //Data da última modificação da permissão

    @ManyToMany(mappedBy = "permissoes")
    private List<Role> papeis; //Relação com os papéis (Role) que possuem esta permissão
}
