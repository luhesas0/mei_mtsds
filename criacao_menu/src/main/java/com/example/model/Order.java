package com.example.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId", unique = true, nullable = false)
    private Integer id;

    // Foreign key reference to Funcionario, managed by another microservice
    @Column(name = "menuId", nullable = true)
    private Integer menuId;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "contactoCliente", nullable = false)
    private String contactoCliente;

    @Column(name = "nomeCliente", nullable = false)
    private String nomeCliente;
}
