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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VeiculoServiceTest {

    @InjectMocks
    private VeiculoService veiculoService;

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private UtilizadorRepository utilizadorRepository;

    @Mock
    private TarefaRepository tarefaRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void atribuirVeiculo_sucesso(){
        //Configuração do cenário
        VeiculoAtribuicaoDTO dto = new VeiculoAtribuicaoDTO();
        dto.setVeiculoId(1L);
        dto.setUtilizadorId(2L);
        dto.setDescricaoTarefa("Entrega de refeições");

        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setFuncionario(null);

        Utilizador funcionario = new Utilizador();
        funcionario.setId(2L);
        funcionario.setAtivo(true);

        when(veiculoRepository.existsById(1L)).thenReturn(true);
        when(veiculoRepository.existsByIdAndFuncionarioIsNull(1L)).thenReturn(true);
        when(utilizadorRepository.findById(2L)).thenReturn(Optional.of(funcionario));
        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculo));

        //Executar o método
        veiculoService.atribuirVeiculo(dto);

        //Verificar interações
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));
        verify(veiculoRepository, times(1)).save(veiculo);

        //Mensagem de sucesso
        System.out.println("Teste 'atribuirVeiculo_sucesso' passou com sucesso!");
    }

    @Test
    void atribuirVeiculo_VeiculoNaoEncontrado(){
        VeiculoAtribuicaoDTO dto = new VeiculoAtribuicaoDTO();
        dto.setVeiculoId(1L);

        when(veiculoRepository.existsById(1L)).thenReturn(false);

        //Executar e verificar exceção
        Exception exception = assertThrows(VeiculoNaoEncontradoException.class, ()-> veiculoService.atribuirVeiculo(dto));
        assertEquals("O veículo com ID '1' não foi encontrado.", exception.getMessage());

        //Mensagem de sucesso
        System.out.println("Teste 'atribuirVeiculo_veiculoNaoEncontrado' passou com sucesso!");
    }

    @Test
    void atribuirVeiculo_VeiculoIndisponivel(){
        VeiculoAtribuicaoDTO dto = new VeiculoAtribuicaoDTO();
        dto.setVeiculoId(1L);

        when(veiculoRepository.existsById(1L)).thenReturn(true);
        when(veiculoRepository.existsByIdAndFuncionarioIsNull(1L)).thenReturn(false);

        //Executar e verificar exceção
        Exception exception = assertThrows(VeiculoIndisponivelException.class, ()-> veiculoService.atribuirVeiculo(dto));
        assertEquals("O veículo selecionado já está atribuído a outro funcionário.", exception.getMessage());

        //Mensagem de sucesso
        System.out.println("Teste 'atribuirVeiculo_VeiculoIndisponivel' passou com sucesso!");
    }

    @Test
    void consultarVeiculosFuncionario_sucesso(){
        Long funcionarioId = 2L;

        Utilizador funcionario = new Utilizador();
        funcionario.setId(funcionarioId);

        Veiculo veiculo = new Veiculo();
        veiculo.setFuncionario(funcionario);

        when(utilizadorRepository.findById(funcionarioId)).thenReturn(Optional.of(funcionario));
        when(veiculoRepository.findAll()).thenReturn(List.of(veiculo));

        //Executar o método
        List<Veiculo> veiculos = veiculoService.consultarVeiculosFuncionario(funcionarioId);

        //Verificar resultado
        assertEquals(1, veiculos.size());
        assertEquals(funcionarioId, veiculos.get(0).getFuncionario().getId());

        //Mensagem com sucesso
        System.out.println("Teste 'consultarVeiculosFuncionario_sucesso' passou com sucesso!");
    }

    @Test
    void atribuirVeiculo_FuncionarioInativo(){
        VeiculoAtribuicaoDTO dto = new VeiculoAtribuicaoDTO();
        dto.setVeiculoId(1L);
        dto.setUtilizadorId(2L);

        Utilizador funcionario = new Utilizador();
        funcionario.setId(2L);
        funcionario.setAtivo(false);

        when(veiculoRepository.existsById(1L)).thenReturn(true);
        when(veiculoRepository.existsByIdAndFuncionarioIsNull(1L)).thenReturn(true);
        when(utilizadorRepository.findById(2L)).thenReturn(Optional.of(funcionario));

        //Executar e verificar exceção
        Exception exception = assertThrows(FuncionarioInativoException.class, ()-> veiculoService.atribuirVeiculo(dto));
        assertEquals("O funcionário está inativo e não pode receber um veículo.", exception.getMessage());

        //Mensagem de sucesso
        System.out.println("Teste 'atribuirVeiculo_FuncionarioInativo' passou com sucesso!");
    }

    @Test
    void consultarVeiculosFuncionario_funcionarioNaoEncontrado(){
        Long funcionarioId = 2L;

        when(utilizadorRepository.findById(funcionarioId)).thenReturn(Optional.empty());

        //Executar e verificar exceção
        Exception exception = assertThrows(FuncionarioNaoEncontradoException.class, ()-> veiculoService.consultarVeiculosFuncionario(funcionarioId));
        assertEquals("O funcionário com ID '2' não foi encontrado.", exception.getMessage());

        //Mensagem com sucesso
        System.out.println("Teste 'consultarVeiculosFuncionario_funcionarioNaoEncontrado' passou com sucesso!");
    }
}
