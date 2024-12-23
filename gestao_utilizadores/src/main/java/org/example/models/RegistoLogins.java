package com.example.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "registo_logins")
public class RegistoLogins {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_registologins", unique = true, nullable = false)
    private String idRegistoLogins; //Identificador único

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador", nullable = false)
    @JsonIgnore
    private Utilizador utilizador; //Fk para a tabela utilizador

    @Column(name = "status_login", nullable = false, length = 50)
    private String statusLogin; //Status do Login: sucesso ou falha

    @Column(name = "data_login", nullable = false)
    private LocalDateTime dataLogin; //Data e hora do login
}
