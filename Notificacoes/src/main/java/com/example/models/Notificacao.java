package com.example.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Representa notificações enviadas a motoristas relacionadas a entregas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notificacoes")
@EntityListeners(AuditingEntityListener.class)
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Identificador único da notificação.

    @Column(nullable = false)
    private Long motoristaId; // ID do motorista notificado.

    @Column(nullable = false)
    private Long entregaId; // ID da entrega associada.

    @Column(nullable = false, length = 500)
    private String mensagem; // Conteúdo da notificação.

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime criadoEm; // Data de criação da notificação.
}
