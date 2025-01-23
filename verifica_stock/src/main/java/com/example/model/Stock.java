package com.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Getter
@Setter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock")
public class Stock implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private double price;

    @ElementCollection
    @CollectionTable(name = "stock_ingredients", joinColumns = @JoinColumn(name = "stock_id"))
    @Column(name = "ingredient")
    private List<String> ingredients;
    @Column(name = "nivel_minimo", nullable = false)
    private Integer nivelMinimo;

}