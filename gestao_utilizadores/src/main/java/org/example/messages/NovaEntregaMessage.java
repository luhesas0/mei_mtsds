package org.example.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NovaEntregaMessage {
    private Long entregaId;
    private String funcionarioNome;
    private String funcionarioNumero;
    private String emailFuncionario;
    private String matriculaVeiculo;
    private String corVeiculo;
    private String categoriaVeiculo;
    private String status; //Pendente, Concluída, Cancelada
    private LocalDateTime dataEntrega;
}
