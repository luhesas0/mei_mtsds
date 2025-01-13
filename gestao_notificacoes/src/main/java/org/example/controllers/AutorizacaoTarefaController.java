package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.enums.TipoAtor;
import org.example.models.AutorizacaoTarefa;
import org.example.models.Tarefa;
import org.example.service.AutorizacaoTarefaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerir endpoints de autorização e tarefas.
 */
@RestController
@RequestMapping("/auth/tarefa")
@RequiredArgsConstructor
public class AutorizacaoTarefaController {

    private final AutorizacaoTarefaService autorizacaoTarefaService;

    /**
     * Atribui uma nova autorização para uma tarefa.
     *
     * @param utilizadorId ID do utilizador.
     * @param tarefaId ID da tarefa.
     * @param acao Ação permitida.
     * @param tipoAtor Tipo de ator (opcional).
     * @return Autorização criada.
     */
    @PostMapping("/atribuir")
    public ResponseEntity<AutorizacaoTarefa> atribuirAutorizacao(
            @RequestParam Long utilizadorId,
            @RequestParam Long tarefaId,
            @RequestParam String acao,
            @RequestParam(required = false) TipoAtor tipoAtor){
                AutorizacaoTarefa autorizacao = autorizacaoTarefaService.atribuirAutorizacao(utilizadorId, tarefaId, acao, tipoAtor);
                return new ResponseEntity<>(autorizacao, HttpStatus.CREATED);
    }

    /**
     * Atualiza uma autorização existente.
     *
     * @param autorizacaoId ID da autorização.
     * @param novaAcao Nova ação permitida.
     * @param novoTipoAtor Novo tipo de ator (opcional).
     * @return Autorização atualizada.
     */
    @PutMapping("/atualizar")
    public ResponseEntity<AutorizacaoTarefa> atualizarAutorizacao(
            @RequestParam Long autorizacaoId,
            @RequestParam String novaAcao,
            @RequestParam(required = false) TipoAtor novoTipoAtor){
        AutorizacaoTarefa autorizacao = autorizacaoTarefaService.atualizarAutorizacao(autorizacaoId, novaAcao, novoTipoAtor);
        return ResponseEntity.ok(autorizacao);
    }

    /**
     * Consulta autorizações por utilizador.
     *
     * @param utilizadorId ID do utilizador.
     * @return Lista de autorizações do utilizador.
     */
    @GetMapping("/consultar/{utilizadorId}")
    public ResponseEntity<List<AutorizacaoTarefa>> consultarAutorizacao(@PathVariable Long utilizadorId){
        List<AutorizacaoTarefa> autorizacoes = autorizacaoTarefaService.consultarAutorizacao(utilizadorId);
        return ResponseEntity.ok(autorizacoes);
    }

    /**
     * Revoga uma autorização existente.
     *
     * @param autorizacaoId ID da autorização a ser revogada.
     * @return Resposta de sucesso ou erro.
     */
    @DeleteMapping("/revogar/{autorizacaoId}")
    public ResponseEntity<Void> revogarAutorizacao(@PathVariable Long autorizacaoId){
        autorizacaoTarefaService.revogarAutorizacao(autorizacaoId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Consulta tarefas atribuídas a um funcionário por ID.
     *
     * @param funcionarioId ID do funcionário.
     * @return Lista de tarefas atribuídas ao funcionário.
     */
    @GetMapping("/funcionario/{funcionarioId}/tarefas")
    public ResponseEntity<List<Tarefa>> consultarTarefasPorFuncionario(@PathVariable Long funcionarioId){
        List<Tarefa> tarefas = autorizacaoTarefaService.consultarTarefasPorFuncionario(funcionarioId);
        return ResponseEntity.ok(tarefas);
    }
}
