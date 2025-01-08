package org.example.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entidade que representa permissões no sistema.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "permissoes")
public class Permissoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id; //Identificador único da permissão

    @Column(name = "nome", nullable = false, length = 100)
    private String nome; //Nome da permissão (Gerir Utilizadores = Administrador)

    @Column(name = "descricao", length = 255)
    private String descricao; //Descrição detalhada da permissão

    @ManyToMany(mappedBy = "permissoes")
    private List<Role> roles; //Relação com os papéis (Role)
}
