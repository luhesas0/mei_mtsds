package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Retorna a média de avaliações de um utilizador.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaAvaliacaoDTO {
    private Long utilizadorId;  // ID do utilizador (cliente ou funcionário)
    private Double mediaNotas;    // Média das notas
    private Long totalAvaliacoes;  // Total de avaliações consideradas
}
