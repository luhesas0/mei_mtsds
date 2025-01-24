package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Retorna os detalhes de uma notificação para visualização.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoResumoDTO {
    private Long id;     // ID da notificação
    private Long utilizadorId; // ID do destinatário (cliente ou funcionário)
    private Long entregaId;   // ID da entrega associada
    private String mensagem;  // Mensagem da notificação
    private String criadoEm;   // Data de criação formatada
}
