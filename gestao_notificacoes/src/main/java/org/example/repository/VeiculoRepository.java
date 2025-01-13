package org.example.repository;

import org.example.models.Veiculo;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações com veículos.
 */
@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    /**
     * Consulta para verificar se um veículo está disponível.
     *
     * @param id ID do veículo.
     * @return true se o veículo está disponível, false caso contrário.
     */
    boolean existsByIdAndFuncionarioIsNull(Long id);
}
