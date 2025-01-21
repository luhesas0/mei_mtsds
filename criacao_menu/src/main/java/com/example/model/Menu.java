package com.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter @Setter
@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // meat, vegetarian, fish

    @ElementCollection
    private List<String> mainDishes;

    @ElementCollection
    private List<String> soups;

    @ElementCollection
    private List<String> desserts;

    @ElementCollection
    private List<String> snacks;

    private Integer numberOfDays;
}