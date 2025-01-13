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
 * Entidade que representa o registo de entregas realizadas.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "registo_entregas")
@EntityListeners(AuditingEntityListener.class)
public class RegistoEntrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id; //Identificador único do registo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarefa_id", nullable = false)
    @JsonIgnore
    private Tarefa tarefa; //FK para a tarefa concluída

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario:id", nullable = false)
    @JsonIgnore
    private Utilizador funcionario; //FK para o funcionario responsável

    @Column(name = "data_entrega", nullable = false)
    private LocalDateTime dataEntrega; //Data e hora da entrega

    @CreatedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate; //Data da última modificação do registo

    /**
     * Construtor adicional para criação de registos de entregas
     *
     * @param tarefa Tarefa concluída.
     * @param funcionario Funcionário responsável pela entrega.
     * @param dataEntrega Data e hora da entrega realizada.
     */
    public RegistoEntrega(Tarefa tarefa,Utilizador funcionario, LocalDateTime dataEntrega){
        this.tarefa = tarefa;
        this.funcionario = funcionario;
        this.dataEntrega = dataEntrega;
    }
}
