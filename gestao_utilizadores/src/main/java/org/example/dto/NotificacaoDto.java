package org.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificacaoDto {
    @NotNull(message = "Mensagem é obrigatória")
    private String mensagem;
    private LocalDateTime dataEnvio;
}
