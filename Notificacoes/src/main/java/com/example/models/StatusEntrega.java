package com.example.models;

import com.example.enums.StatusEntregaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Representa o status de uma entrega,
 * incluindo informações sobre o funcionário (entregador).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "status_entregas")
public class StatusEntrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Identificador único do status da entrega.

    @Column(name = "entrega_id", nullable = false)
    private Long entregaId; // ID da entrega associada.

    @Column(name = "utilizador_id", nullable = false)
    private Long utilizadorId; // ID do funcionário responsável pela entrega.

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusEntregaEnum status;  // Status da entrega ("ACEITO_PARA_ENTREGA", "REJEITADO"...)

    @Column(name= "veiculo", length = 50)
    private String veiculo; // Veículo atribuído para a entrega.

    @Column(name= "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm; // Data de atualização do status.
}
