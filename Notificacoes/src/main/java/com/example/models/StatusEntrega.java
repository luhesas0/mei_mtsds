package com.example.models;

import com.example.enums.StatusEntregaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Representa o status de uma entrega,
 * incluindo informações sobre o motorista, veículo e status atual.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "status_entregas")
@EntityListeners(AuditingEntityListener.class)
public class StatusEntrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Identificador único do status da entrega.

    @Column(nullable = false)
    private Long entregaId; // ID da entrega associada.

    @Column(nullable = false)
    private Long motoristaId; // ID do motorista responsável.

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEntregaEnum status;  // Status da entrega ("ACEITO_PARA_ENTREGA", "REJEITADO"...)

    @Column(nullable = false)
    private String veiculo; // Veículo atribuído para a entrega.

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime atualizadoEm; // Data de atualização do status.
}
