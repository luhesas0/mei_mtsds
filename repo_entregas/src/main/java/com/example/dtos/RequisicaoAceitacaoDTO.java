package com.example.dtos;

import lombok.Data;

@Data
public class RequisicaoAceitacaoDTO {
    private Integer ordemTrabalhoId;
    private Integer funcionarioId;
    private boolean aceite;
    private Integer capacidadeVeiculo;
}
