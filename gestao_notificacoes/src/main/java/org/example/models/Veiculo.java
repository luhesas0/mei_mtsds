package org.example.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidade para representar veículos associados a funcionários.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "veiculos")
@EntityListeners(AuditingEntityListener.class)
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id; //Identificador único do veículo

    @Column(name = "modelo", nullable = false, length = 100)
    private String modelo; //Modelo do veiculo (ex: Renault Zoe)

    @Column(name = "matricula", nullable = false, unique = true, length = 15)
    private String matricula; //Matricula do veiculo (ex: AB-12-CD)

    @Column(name = "capacidade", nullable = false)
    private Integer capacidade; //Capacidade de carga do veículo (ex: qtd 200 refeicoes)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funcionario_id", nullable = true)
    private Utilizador funcionario; //FK para o funcionários responsável pelo veiculo

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarefa> tarefas; //Lista de tarefas atribuídas ao veículo

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate; //Data de criação do veículo (auditoria)

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate; //Data da última modificação do veículo (auditoria)

    /**
     * Construtor adicional para criação de veículos com os campos obrigatórios
     * @param modelo Modelo do veículo.
     * @param matricula Matrícula do veículo.
     * @param capacidade Capacidade de carga do veículo.
     */
    public Veiculo(String modelo, String matricula, Integer capacidade){
        this.modelo = modelo;
        this.matricula = matricula;
        this.capacidade = capacidade;
    }
}
