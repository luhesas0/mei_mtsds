package org.example.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, nullable = false)
    private String roles; //Identificador único

    @Column(name = "nome", nullable = false, length = 255)
    private String nome; //Nome do papel (Administrador, Funcionário, Cliente)
}
