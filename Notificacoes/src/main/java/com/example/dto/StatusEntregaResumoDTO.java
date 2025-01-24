package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Exibir o status atual de uma entrega.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusEntregaResumoDTO {
    private Long entregaId;    // ID da entrega
    private Long utilizadorId;  // ID do responsável pela entrega (funcionário)
    private String status;    // Status atual
    private String atualizadoEm;  // Data da última atualização formatada
}
