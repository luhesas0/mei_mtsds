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
@Table(name = "ordensTrabalho")
public class OrdemTrabalho implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ordemTrabalho", unique = true, nullable = false)
    private Integer orderId;

    // Foreign key reference to Funcionario, managed by another microservice
    @Column(name = "id_funcionario", nullable = false)
    private Integer funcionarioId;

    // Foreign key reference to Menu, managed by another microservice
    @Column(name = "id_menu", nullable = false)
    private Integer menuId;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private OrderStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_criacao", nullable = false)
    private Date dataCriacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_entrega", nullable = false)
    private Date dataEntrega;

    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "endereco_entrega", nullable = false)
    private String enderecoEntrega;

    @Column(name = "contacto", nullable = false)
    private String contacto;

    @Column(name = "nome_cliente", nullable = false)
    private String nomeCliente;
}
