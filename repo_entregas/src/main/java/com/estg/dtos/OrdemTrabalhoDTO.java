package com.estg.dtos;

import com.estg.enums.OrderStatus;
import lombok.*;

import java.util.Date;

@Getter
@Setter
public class OrdemTrabalhoDTO {
    private Integer id;
    private Integer funcionarioId;
    private Integer menuId;
    private Integer quantidade;
    private OrderStatus status;
    private Date dataCriacao;
    private Date dataEntrega;
    private String enderecoEntrega;
    private String contacto;
    private String nomeCliente;
}
