package org.example.service;

import org.example.enums.TipoAtor;
import org.example.exceptions.*;
import org.example.models.AutorizacaoTarefa;
import org.example.models.Tarefa;
import org.example.models.Utilizador;
import org.example.repository.AutorizacaoTarefaRepository;
import org.example.repository.TarefaRepository;
import org.example.repository.UtilizadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AutorizacaoTarefaServiceTest {

    @InjectMocks
    private AutorizacaoTarefaService autorizacaoTarefaService;

    @Mock
    private AutorizacaoTarefaRepository autorizacaoTarefaRepository;

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private UtilizadorRepository utilizadorRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void atribuirAutorizacao_sucesso(){
        Long utilizadorId = 1L;
        Long tarefaId = 2L;
        String acao = "CRIAR";
        TipoAtor tipoAtor = TipoAtor.FUNCIONARIO;

        Utilizador utilizador = new Utilizador();
        utilizador.setId(utilizadorId);

        Tarefa tarefa = new Tarefa();
        tarefa.setId(tarefaId);

        when(utilizadorRepository.findById(utilizadorId)).thenReturn(Optional.of(utilizador));
        when(tarefaRepository.findById(tarefaId)).thenReturn(Optional.of(tarefa));
        when(autorizacaoTarefaRepository.existsByUtilizadorIdAndTarefaId(utilizadorId, tarefaId)).thenReturn(false);

        autorizacaoTarefaService.atribuirAutorizacao(utilizadorId, tarefaId, acao, tipoAtor);

        verify(autorizacaoTarefaRepository, times(1)).save(any(AutorizacaoTarefa.class));

        //Mensagem de sucesso
        System.out.println("Teste 'atribuirAutorizacao_sucesso' passou com sucesso!");
    }

    @Test
    void atribuirAutorizacao_duplicada(){
        Long utilizadorId = 1L;
        Long tarefaId = 2L;
        String acao = "CRIAR";

        Utilizador utilizador = new Utilizador();
        utilizador.setId(utilizadorId);

        Tarefa tarefa = new Tarefa();
        tarefa.setId(tarefaId);

        //Simular existência de utilizador e tarefa
        when(utilizadorRepository.findById(utilizadorId)).thenReturn(Optional.of(utilizador));
        when(tarefaRepository.findById(tarefaId)).thenReturn(Optional.of(tarefa));

        //Simular autorização duplicada
        when(autorizacaoTarefaRepository.existsByUtilizadorIdAndTarefaId(utilizadorId, tarefaId)).thenReturn(true);

        //Testar exceção de duplicidade
        Exception exception = assertThrows(AutorizacaoJaExisteException.class, ()->
                autorizacaoTarefaService.atribuirAutorizacao(utilizadorId, tarefaId, acao, null));

        assertEquals("A autorização já existe: Utilizador ID:1, Tarefa ID:2", exception.getMessage());

        //Verificar que o método save não foi chamado
        verify(autorizacaoTarefaRepository, never()).save(any(AutorizacaoTarefa.class));

        //Mensagem de sucesso
        System.out.println("Teste 'atribuirAutorizacao_duplicada' passou com sucesso!");
    }

    @Test
    void atribuirAutorizacao_acaoInvalida(){
        Long utilizadorId = 1L;
        Long tarefaId = 2L;
        String acao = "INVALIDO";

        Utilizador utilizador = new Utilizador();
        utilizador.setId(utilizadorId);

        Tarefa tarefa = new Tarefa();
        tarefa.setId(tarefaId);

        //Configuração do mock para garantir que o utilizador e a tarefa existem
        when(utilizadorRepository.findById(utilizadorId)).thenReturn(Optional.of(utilizador));
        when(tarefaRepository.findById(tarefaId)).thenReturn(Optional.of(tarefa));

        //Testar comportamento para ação inválida
        Exception exception = assertThrows(ParametroInvalidoException.class, ()->
                autorizacaoTarefaService.atribuirAutorizacao(utilizadorId, tarefaId, acao, null));

        assertTrue(exception.getMessage().contains("AçãoINVALIDO"));

        //Mensagem de sucesso
        System.out.println("Teste 'atribuirAutorizacao_acaoInvalida' passou com sucesso!");
    }

    @Test
    void consultarAutorizacao_sucesso(){
        Long utilizadorId = 1L;
        List<AutorizacaoTarefa> autorizacoes = List.of(new AutorizacaoTarefa());

        when(autorizacaoTarefaRepository.findByUtilizadorId(utilizadorId)).thenReturn(autorizacoes);

        List<AutorizacaoTarefa> result = autorizacaoTarefaService.consultarAutorizacao(utilizadorId);

        assertEquals(1, result.size());
        verify(autorizacaoTarefaRepository, times(1)).findByUtilizadorId(utilizadorId);

        //Mensagem de sucesso
        System.out.println("Teste 'consultarAutorizacao_sucesso' passou com sucesso!");
    }

    @Test
    void revogarAutorizacao_sucesso(){
        Long autorizacaoId = 1L;

        when(autorizacaoTarefaRepository.existsById(autorizacaoId)).thenReturn(true);

        autorizacaoTarefaService.revogarAutorizacao(autorizacaoId);

        verify(autorizacaoTarefaRepository, times(1)).deleteById(autorizacaoId);

        //Mensagem de sucesso
        System.out.println("Teste 'revogarAutorizacao_sucesso' passou com sucesso!");
    }

    @Test
    void revogarAutorizacao_naoEncontrada(){
        Long autorizacaoId = 1L;

        when(autorizacaoTarefaRepository.existsById(autorizacaoId)).thenReturn(false);

        Exception exception = assertThrows(RecursoNaoEncontradoException.class, ()->
                autorizacaoTarefaService.revogarAutorizacao(autorizacaoId));

        assertEquals("O recurso 'Autorização com ID:1' não foi encontrado.", exception.getMessage());

        //Mensagem de sucesso
        System.out.println("Teste 'revogarAutorizacao_naoEncontrada' passou com sucesso!");
    }

    @Test
    void consultarTarefasPorFuncionario_sucesso(){
        Long funcionarioId = 1L;
        List<Tarefa> tarefas = List.of(new Tarefa());

        when(tarefaRepository.findByFuncionarioId(funcionarioId)).thenReturn(tarefas);

        List<Tarefa> result = autorizacaoTarefaService.consultarTarefasPorFuncionario(funcionarioId);

        assertEquals(1, result.size());
        verify(tarefaRepository, times(1)).findByFuncionarioId(funcionarioId);

        //Mensagem de sucesso
        System.out.println("Teste 'consultarTarefasPorFuncionario_sucesso' passou com sucesso!");
    }

    @Test
    void verificarAutorizacao_sucesso(){
        Long utilizadorId = 1L;
        Long tarefaId = 2L;

        when(autorizacaoTarefaRepository.existsByUtilizadorIdAndTarefaId(utilizadorId, tarefaId)).thenReturn(true);

        boolean result = autorizacaoTarefaService.verificarAutorizacao(utilizadorId, tarefaId);

        assertTrue(result);
        verify(autorizacaoTarefaRepository, times(1)).existsByUtilizadorIdAndTarefaId(utilizadorId, tarefaId);

        //Mensagem de sucesso
        System.out.println("Teste 'verificarAutorizacao_sucesso' passou com sucesso!");
    }
}
