package org.example.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "registo_logins")
@EntityListeners(AuditingEntityListener.class)
public class RegistoLogins {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id; //Identificador único

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilizador", nullable = false)
    @JsonIgnore
    private Utilizador utilizador; //FK para a tabela utilizadores

    @Column(name = "status_login", nullable = false)
    private Boolean statusLogin; //Status do login: sucesso ou falha

    @Column(name = "data_login", nullable = false)
    private LocalDateTime dataLogin; //Data e hora do login

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;
}
