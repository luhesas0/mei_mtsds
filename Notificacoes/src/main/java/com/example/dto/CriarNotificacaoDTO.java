package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa os dados necessários para criar uma notificação.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriarNotificacaoDTO {
    private Long utilizadorId;  // ID do destinatário (cliente ou funcionário)
    private Long entregaId;     // ID da entrega associada
    private String mensagem;   // Mensagem da notificação
}
