package com.estg.dtos;

import com.estg.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrdemTrabalhoDTO {
    private Integer orderId;
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
