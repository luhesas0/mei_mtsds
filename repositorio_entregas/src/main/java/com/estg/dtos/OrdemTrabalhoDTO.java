package com.estg.dtos;

import com.estg.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrdemTrabalhoDTO {
    private Integer order_id;
    private Integer menu_id;
    private Integer quantidade;
    private OrderStatus status;
    private String dataCriacao;
    private String dataEntrega;
    private String enderecoEntrega;
    private String contacto;
    private String nomeCliente;
}
