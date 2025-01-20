package com.example.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Entidade que representa o registo de permissões atribuidas aos utilizadores
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "registo_permissao")
public class RegistoPermissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id; //Identificador único do registo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador", nullable = false)
    @JsonIgnore
    private Utilizador utilizador; //Referência ao utilizador associado

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_permissao", nullable = false)
    @JsonIgnore
    private Permissao permissao; //Referência à permissão atribuída

    @Column(name = "ativo", nullable = false)
    private Boolean ativo; //Indica se a permissão está ativa ou revogada

    @CreatedDate
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao; //Data de criação do regisyo

    @LastModifiedDate
    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao; //Data da última modificação do registo
}