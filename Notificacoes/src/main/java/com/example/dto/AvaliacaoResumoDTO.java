package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Retorna os detalhes de uma avaliação para visualização.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoResumoDTO {
    private long id;           // ID da avaliação
    private Long entregaId;   // ID da entrega associada
    private Long utilizadorId;    // ID do utilizador avaliado (cliente ou funcionário)
    private String criterio;   // Critério avaliado
    private Integer nota;      // Nota atribuída
    private String comentario;  // Comentário opcional
    private String criadoEm;   // Data de criação formatada
}
