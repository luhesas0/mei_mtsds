package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa uma mensagem de e-mail para envio via RabbitMQ
 * e integração com serviços de notificação.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessage {
    private String to; // Endereço de e-mail do destinatário.
    private String from; // Endereço de e-mail do remetente.
    private String subject; // Assunto do e-mail.
    private String body; // Corpo do e-mail.
}
