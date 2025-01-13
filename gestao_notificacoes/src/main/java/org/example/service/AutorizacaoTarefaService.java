package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.enums.TipoAtor;
import org.example.exceptions.AutorizacaoJaExisteException;
import org.example.exceptions.ParametroInvalidoException;
import org.example.exceptions.RecursoNaoEncontradoException;
import org.example.models.AutorizacaoTarefa;
import org.example.models.Tarefa;
import org.example.models.Utilizador;
import org.example.repository.AutorizacaoTarefaRepository;
import org.example.repository.TarefaRepository;
import org.example.repository.UtilizadorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável por gerir autorizações de tarefas.
 */
@Service
@RequiredArgsConstructor
public class AutorizacaoTarefaService {

    private final AutorizacaoTarefaRepository autorizacaoTarefaRepository;
    private final TarefaRepository tarefaRepository;
    private final UtilizadorRepository utilizadorRepository;

    /**
     * Atribui uma autorização para uma tarefa específica.
     *
     * @param utilizadorId ID do utilizador.
     * @param tarefaId ID da tarefa.
     * @param acao Ação permitida (CRIAR, LER, ATUALIZAR, REMOVER).
     * @param tipoAtor Tipo de ator associado (opcional).
     * @return A autorização criada.
     */
    public AutorizacaoTarefa atribuirAutorizacao(Long utilizadorId, Long tarefaId, String acao, TipoAtor tipoAtor){
        Utilizador utilizador = utilizadorRepository.findById(utilizadorId)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Utilizador com ID:" + utilizadorId));

        Tarefa tarefa = tarefaRepository.findById(tarefaId)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Tarefa com ID:" + tarefaId));

        if (autorizacaoTarefaRepository.existsByUtilizadorIdAndTarefaId(utilizadorId, tarefaId)){
            throw new AutorizacaoJaExisteException(
                    String.format("Utilizador ID:%d, Tarefa ID:%d", utilizadorId, tarefaId)
            );
        }

        if (!acao.matches("CRIAR|LER|ATUALIZAR|REMOVER")){
            throw new ParametroInvalidoException("Ação" + acao);
        }

        AutorizacaoTarefa autorizacaoTarefa = new AutorizacaoTarefa(utilizador, tarefa, acao, tipoAtor);
        return autorizacaoTarefaRepository.save(autorizacaoTarefa);
    }

    /**
     * Atualiza uma autorização existente.
     *
     * @param autorizacaoId ID da autorização.
     * @param novaAcao Nova ação permitida.
     * @param novoTipoAtor Novo tipo de ator (opcional).
     * @return A autorização atualizada.
     */
    public AutorizacaoTarefa atualizarAutorizacao(Long autorizacaoId, String novaAcao, TipoAtor novoTipoAtor){
        AutorizacaoTarefa autorizacaoTarefa = autorizacaoTarefaRepository.findById(autorizacaoId)
                .orElseThrow(()-> new RecursoNaoEncontradoException("Autorização com ID:" + autorizacaoId));

        if (!novaAcao.matches("CRIAR|LER|ATUALIZAR|REMOVER")){
            throw new ParametroInvalidoException("Ação" + novaAcao);
        }

        autorizacaoTarefa.setAcao(novaAcao);
        autorizacaoTarefa.setTipoAtor(novoTipoAtor);

        return autorizacaoTarefaRepository.save(autorizacaoTarefa);
    }

    /**
     * Consulta utilizadorId ID do utilizador.
     * @return Lista de autorizações do utilizador.
     */
    public List<AutorizacaoTarefa> consultarAutorizacao(Long utilizadorId){
        return autorizacaoTarefaRepository.findByUtilizadorId(utilizadorId);
    }

    /**
     * Revoga uma autorização existente.
     *
     * @param autorizacaoId ID da autorização a ser revogada.
     */
    public void revogarAutorizacao(Long autorizacaoId){
        if (!autorizacaoTarefaRepository.existsById(autorizacaoId)){
            throw new RecursoNaoEncontradoException("Autorização com ID:" + autorizacaoId);
        }
        autorizacaoTarefaRepository.deleteById(autorizacaoId);
    }

    /**
     * Consulta tarefas atribuídas a um funcionário por ID.
     *
     * @param funcionarioId ID do funcionário.
     * @return Lista de tarefas atribuídas.
     */
    public List<Tarefa> consultarTarefasPorFuncionario(Long funcionarioId){
        return tarefaRepository.findByFuncionarioId(funcionarioId);
    }

    /**
     * Verifica se uma autorização específica existe.
     *
     * @param utilizadorId ID do utilizador.
     * @param tarefaId ID da tarefa.
     * @return True se a autorização existir, caso contrário False.
     */
    public boolean verificarAutorizacao(Long utilizadorId, Long tarefaId){
        return autorizacaoTarefaRepository.existsByUtilizadorIdAndTarefaId(utilizadorId, tarefaId);
    }
}
