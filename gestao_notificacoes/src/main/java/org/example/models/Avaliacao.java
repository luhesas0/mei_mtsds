package org.example.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Entidade que representa avaliações feitas por clientes sobre entregas.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "avaliacoes")
@EntityListeners(AuditingEntityListener.class)
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id; //Identificador único da avaliação

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entrega_id", nullable = false)
    @JsonIgnore
    private RegistoEntrega entrega; //FK para o registo da entrega avaliada

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnore
    private Utilizador cliente; //Fk para o cliente que realizou a avaliação

    @Column(name = "nota", nullable = false)
    private Integer nota; //Nota da avaliação (1-5, Escala de Likert)

    @Column(name = "comentarios", length = 500)
    private String comentarios; //Comentários opcionais sobre a entrega

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate; //Data de criação da avaliação

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate; //Data de última modificação da avaliação

    /**
     * Construtor adicional para criação de avaliações
     *
     * @param entrega Entrega avaliada.
     * @param cliente Cliente que realizou a avaliação
     * @param nota Nota da avaliação (Escala de Likert).
     * @param comentarios Comentários opcionais sobre a entrega.
     */
    public Avaliacao(RegistoEntrega entrega, Utilizador cliente, Integer nota, String comentarios){
        this.entrega = entrega;
        this.cliente = cliente;
        this.nota = nota;
        this.comentarios = comentarios;
    }
}
