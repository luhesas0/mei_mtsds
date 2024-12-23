package com.example.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "utilizadores_roles")
public class UtilizadoresRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_utilizadoresroles", unique = true, nullable = false)
    private String id_utilizadoresroles; //Identificador único

    @ManyToOne
    @JoinColumn(name = "id_utilizador", nullable = false)
    private Utilizador utilizador; //FK para a tabela utilizador

    @ManyToOne
    @JoinColumn(name = "id_role", nullable = false)
    private Role role; //Fk para a tabela roles
}
