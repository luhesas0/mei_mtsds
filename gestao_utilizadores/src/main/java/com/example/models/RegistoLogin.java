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
 * Entidade que representa o registo de logins de utilizadores.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "registo_login")
public class RegistoLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id; //Identificador único

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador", nullable = false)
    @JsonIgnore
    private Utilizador utilizador; //Referência ao utilizador que realizou o login

    @Column(name = "ip_address", length = 50)
    private String ipAddress; //Endereço IP do login

    @CreatedDate
    @Column(name = "data_login", nullable = false, updatable = false)
    private LocalDateTime dataLogin;  //Data e hora de login

    @LastModifiedDate
    @Column(name = "ultima_modificacao")
    private LocalDateTime ultimaModificacao; //Data de última modificação do registo
}
