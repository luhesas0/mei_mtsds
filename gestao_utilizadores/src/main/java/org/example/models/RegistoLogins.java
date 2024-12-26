package org.example.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "registologins")
public class RegistoLogins {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_registologins", unique = true, nullable = false)
    private String id; //Identificador único

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    @JsonIgnore
    private Utilizador utilizador; //FK para a tabela utilizador

    @Column(name = "statuslogin", nullable = false, length = 50)
    private String status_login; //Status do login: sucesso ou falha

    @Column(name = "datalogin", nullable = false)
    private LocalDateTime data_login; //Data e hora do login
}
