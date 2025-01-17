package com.example.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para encapsular dados necessários para atribuição de veículos.
 */
@Getter
@Setter
public class VeiculoAtribuicaoDTO {
    private Long veiculoId; //ID do veículo a ser atribuído
    private Long utilizadorId; //ID do funcionário ao qual o veículo será atribuído
    private String descricaoTarefa; //// Descrição da tarefa atribuída
}
