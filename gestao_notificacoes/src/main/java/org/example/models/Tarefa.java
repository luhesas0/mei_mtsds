package org.example.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.StatusTarefa;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Entidade para representar as tarefas atribuídas aos funcionários.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "tarefas")
@EntityListeners(AuditingEntityListener.class)
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id; //Identificador único da tarefa

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao; //Descrição detalhada da tarefa

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusTarefa status; //Status da tarefa (Enumeração: POR_REALIZAR,EM_CURSO, CONCLUIDA) )

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id",nullable = false)
    private Utilizador funcionario; //FK para o funcionario responsavel pela tarefa

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veiculo_id", nullable = true)
    private Veiculo veiculo; //FK para o veículo associado à tarefa

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate; //Data de criação da tarefa (auditoria)

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate; //Data da última modificação da tarefa (auditoria)

    /**
     * Construtor adicional para criação de tarefas com os campos obrigatórios.
     *
     * @param descricao Descrição da tarefa.
     * @param status Status inicial da tarefa.
     * @param funcionario Funcionário responsável pela tarefa.
     * @param veiculo Veículo associado à tarefa.
     */
    public Tarefa(String descricao, StatusTarefa status, Utilizador funcionario, Veiculo veiculo){
        this.descricao = descricao;
        this.status = status;
        this.funcionario = funcionario;
        this.veiculo = veiculo;
    }
}
