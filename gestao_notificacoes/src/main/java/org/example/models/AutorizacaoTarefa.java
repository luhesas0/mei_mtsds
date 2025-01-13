package org.example.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.TipoAtor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * Entidade para gerir autorizações de tarefas no sistema.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "autorizacoes_tarefas")
public class AutorizacaoTarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id; //Identificador único da autorização tarefa

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilizador_id", nullable = false)
    private Utilizador utilizador; //Fk para o utilizador associado à autorização tarefa

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarefa_id", nullable = false)
    private Tarefa tarefa; //FK para a tarefa associada

    @Column(name = "acao", nullable = false, length = 20)
    private String acao; //Ação permitida (CRIAR, LER, ATUALIZAR, REMOVER)

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_ator", nullable = true, length = 50)
    private TipoAtor tipoAtor; //Tipo de ator associado à autorização tarefa (opcional)

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate; //Data de criação da autorização tarefa

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate; //Data da última modificação da autorização tarefa

    /**
     * Construtor para criar uma autorização de tarefa básica.
     *
     * @param utilizador Utilizador associado.
     * @param tarefa Tarefa associada.
     * @param acao Ação permitida.
     * @param tipoAtor Tipo de ator (opcional).
     */
    public AutorizacaoTarefa(Utilizador utilizador, Tarefa tarefa, String acao, TipoAtor tipoAtor){
        this.utilizador = utilizador;
        this.tarefa = tarefa;
        this.acao = acao;
        this.tipoAtor = tipoAtor;
    }
}
