package com.example.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.example.enums.Criterio;

import java.time.LocalDateTime;

/**
 * Representa uma avaliação feita por um cliente sobre uma entrega.
 * Inclui critérios de avaliação, nota, e um comentário opcional.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "avaliacoes")
@EntityListeners(AuditingEntityListener.class)
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único da avaliação.

    @Column(nullable = false)
    private Long entregaId; // ID da entrega associada (obtido do microserviço de repositório).

    @Column(nullable = false)
    private Long motoristaId; // ID do motorista avaliado (obtido do microserviço de gestão de utilizadores).

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Criterio criterio; // Critério avaliado, definido no enum `Criterio`.

    @Column(nullable = false)
    private Integer nota; // Escala Likert (1 a 5).

    @Column(length = 500)
    private String comentario; // Comentário opcional fornecido pelo cliente.

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime criadoEm; // Data de criação da avaliação.


}
