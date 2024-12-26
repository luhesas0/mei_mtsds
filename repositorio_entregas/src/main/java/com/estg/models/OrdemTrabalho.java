package com.estg.models;

import com.estg.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ordens_trabalho")
public class OrdemTrabalho implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ordemTrabalho", unique = true, nullable = false)
    private Integer order_id;

    @Column(name = "id_menu", nullable = false)
    private Integer menu_id;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "status", nullable = false, length = 50)
    private OrderStatus status;

    @Column(name = "data_criacao", nullable = false)
    private Date dataCriacao;

    @Column(name = "data_entrega", nullable = false)
    private Date dataEntrega;

    @Column(name = "observacoes", nullable = true)
    private String observacoes;

    @Column(name = "endereco_entrega", nullable = false)
    private String enderecoEntrega;

    @Column(name = "contacto", nullable = false)
    private String contacto;

    @Column(name = "nome_cliente", nullable = false)
    private String nomeCliente;
}
