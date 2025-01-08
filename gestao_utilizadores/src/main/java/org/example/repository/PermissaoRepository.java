package org.example.repository;

import org.example.models.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório para a entida Permissões.
 *
 * Objetivos:
 * - Permitir a gestão das permissões do sistema.
 * - Suportar consultas específicas para encontrar permissões por nome.
 *
 * Requisitos Funcionais:
 * - Criar, atualizar e consultar permissões.
 * - Relacionar permissões aos papéis (roles) atribuidos aos utilizadores.
 *
 * Casos de Uso:
 * - Adicionar novas permissões para papéis existentes.
 * - Buscar permissões específicas associadas a papéis.
 */
public interface PermissoesRepository extends JpaRepository<Permissao, Integer>{
    /**
     * Busca uma permissão pelo nome.
     *
     * @param nome Nome da permissão.
     * @return Optional contendo a permissão, se encontrada.
     */
    Optional<Permissao> findByNome(String nome);

    /**
     * Verifica se uma permissão com o nome especificado já existe.
     *
     * @param nome Nome da permissão.
     * @return true se a permissão existe, caso contrário false.
     */
    boolean existsByNome(String nome);
}
