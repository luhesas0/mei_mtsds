package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Representa os dados para atualizar o status de uma entrega.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtualizarStatusEntregaDTO {
    private Long entregaId;  // ID da entrega
    private Long utilizadorId;   // ID do funcionário responsável
    private String status;     // Novo status (enum StatusEntregaEnum)
    private String veiculo;   // Veículo associado (opcional)
}
