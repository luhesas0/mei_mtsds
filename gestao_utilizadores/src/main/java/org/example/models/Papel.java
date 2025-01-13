package org.example.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidade que representa os papéis de utilizadores no sistema.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
@EntityListeners(AuditingEntityListener.class)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id; //Identificador único

    @Column(name = "nome", nullable = false, length = 50)
    private String nome; //Nome do papel (Administrador, Funcionário, Cliente)

    @Column(name= "descricao",nullable = true,length = 255)
    private String descricao; //Descrição opcional do papel

    @CreatedDate
    @Column(name = "created_date", nullable = false,updatable = false)
    private LocalDateTime createdDate; //Data de criação

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate; //Data da última modificação

    @ManyToMany
    @JoinTable(
            name = "roles_permissoes",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permissao_id")
    )
    private List<Permissao> permissoes; //Lista de permissões associadas ao papel
}
