package org.example.service;

import org.example.dto.VeiculoAtribuicaoDTO;
import org.example.enums.StatusTarefa;
import org.example.exceptions.*;
import org.example.models.Tarefa;
import org.example.models.Utilizador;
import org.example.models.Veiculo;
import org.example.repository.TarefaRepository;
import org.example.repository.UtilizadorRepository;
import org.example.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para operações relacionadas a veículos.
 */
@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private UtilizadorRepository utilizadorRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    /**
     * Atribui um veiculo a um funcionário com validações.
     *
     * @param dto Dados de atribuição encapsulados.
     */
    public void atribuirVeiculo(VeiculoAtribuicaoDTO dto){
        //Verificar se o veículo existe e está disponível
        if(!veiculoRepository.existsById(dto.getVeiculoId())){
            throw new VeiculoNaoEncontradoException(dto.getVeiculoId());
        }
        if(!veiculoRepository.existsByIdAndFuncionarioIsNull(dto.getVeiculoId())){
            throw new VeiculoIndisponivelException("O veículo selecionado já está atribuído a outro funcionário.");
        }

        //Verificar se o funcionário existe e está ativo
        Utilizador funcionario = utilizadorRepository.findById(dto.getUtilizadorId())
                .orElseThrow(()-> new FuncionarioNaoEncontradoException(dto.getUtilizadorId()));
        if(!funcionario.getAtivo()){
            throw new FuncionarioInativoException("O funcionário está inativo e não pode receber um veículo.");
        }

        // Buscar o veículo
        Veiculo veiculo = veiculoRepository.findById(dto.getVeiculoId())
                .orElseThrow(()-> new VeiculoNaoEncontradoException(dto.getVeiculoId()));


        // Criar a tarefa (perfil) e associar ao funcionário e veículo
        Tarefa tarefa = new Tarefa();
        tarefa.setDescricao(dto.getDescricaoTarefa());
        tarefa.setFuncionario(funcionario);
        tarefa.setVeiculo(veiculo);
        tarefa.setStatus(StatusTarefa.POR_REALIZAR);
        tarefa.setCreatedDate(LocalDateTime.now());

        tarefaRepository.save(tarefa);

        //Atualizar o veículo com o funcionário atribuído
        veiculo.setFuncionario(funcionario);
        veiculoRepository.save(veiculo);
    }

    /**
     * Consulta os veículos atribuídos a um funcionário.
     *
     * @param funcionarioId ID do funcionario.
     * @return Lista de veículos atribuidos.
     */
    public List<Veiculo> consultarVeiculosFuncionario(Long funcionarioId){
        //Verificar se o funcionário existe
        Utilizador funcionario = utilizadorRepository.findById(funcionarioId)
                .orElseThrow(()-> new FuncionarioNaoEncontradoException(funcionarioId));

        //Buscar veiculos atribuidos ao funcionário
        return veiculoRepository.findAll().stream()
                .filter(veiculo -> veiculo.getFuncionario() != null && veiculo.getFuncionario().getId().equals(funcionarioId))
                .collect(Collectors.toList());
    }
}
