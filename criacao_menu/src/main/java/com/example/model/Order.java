package com.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    private Integer quantidade; // Quantidade do menu no pedido
    private String nomeCliente; // Nome do cliente
    private String contactoCliente; // Contato do cliente
    private String moradaDestino; // Endereço de entrega
}
