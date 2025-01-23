package com.example.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Representa notificações enviadas para utilizadores (FUNCIONARIO_ENTREGA ou clientes).
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

    @Column(name = "utilizador_id", nullable = false)
    private Long utilizadorId; // ID do destinatário (cliente ou funcionário).

    @Column(name = "entrega_id", nullable = false)
    private Long entregaId; // ID da entrega associada.

    @Column(name = "mensagem", nullable = false, length = 500)
    private String mensagem; // Conteúdo da notificação.

    @CreatedDate
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm; // Data de criação da notificação.
}
