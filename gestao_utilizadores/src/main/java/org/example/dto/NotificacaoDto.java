package org.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificacaoDto {
    @NotNull(message = "ID do utilizador é obrigatório")
    private Long utilizadorId;

    @NotNull(message = "Mensagem é obrigatória")
    private String mensagem;

    private LocalDateTime dataEnvio;
}
