package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.enums.Criterio;

/**
 * Representa os dados necessários para registar uma nova avaliação.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriarAvaliacaoDTO {
    private Long entregaId;   // ID da entrega avaliada
    private Long utilizadorId;  // ID do cliente ou funcionário avaliado
    private String criterio;   // Critério de avaliação (enum Criterio)
    private Integer nota;       // Nota da avaliação (1-5)
    private String comentario;  // Comentário opcional
}
