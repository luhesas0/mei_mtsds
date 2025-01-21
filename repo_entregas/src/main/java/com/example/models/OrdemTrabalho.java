package com.example.models;

import com.example.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter @Setter
@EqualsAndHashCode
@Table(name = "ordensTrabalho")
@EntityListeners(AuditingEntityListener.class)
public class OrdemTrabalho implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ordemTrabalho", unique = true, nullable = false)
    private Integer orderId;

    // Foreign key reference to Funcionario, managed by another microservice
    @Column(name = "id_funcionario", nullable = true)
    private Integer funcionarioId;

    // Foreign key reference to Menu, managed by another microservice
    @Column(name = "id_menu", nullable = false)
    private Integer menuId;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50)
    private OrderStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_criacao", nullable = false)
    private Date dataCriacao;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_entrega", nullable = true)
    private Date dataEntrega;

    @Column(name = "rota_entrega", nullable = false)
    private String enderecoEntrega;

    @Column(name = "contacto", nullable = false)
    private String contacto;

    @Column(name = "nome_cliente", nullable = false)
    private String nomeCliente;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;
}
