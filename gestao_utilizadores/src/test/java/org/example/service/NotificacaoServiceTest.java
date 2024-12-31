package org.example.service;

import org.example.dto.NotificacaoDto;
import org.example.models.Notificacoes;
import org.example.models.Utilizador;
import org.example.repository.NotificacoesRepository;
import org.example.repository.UtilizadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotificacaoServiceTest {

    @InjectMocks
    private NotificacaoService notificacaoService;

    @Mock
    private NotificacoesRepository notificacoesRepository;

    @Mock
    private UtilizadorRepository utilizadorRepository;

    @Mock
    private ModelMapper modelMapper;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp(){
        mocks = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEnviarNotificacao_Sucesso(){
        // Configuração do ID do utilizador e da mensagem
        Long userId = 1L;
        String mensagem = "Nova tarefa atribuída.";

        // Simulação de um utilizador existente
        Utilizador utilizador = new Utilizador();
        utilizador.setId(userId);
        utilizador.setEmail("test@example.com");

        // Configuração do mock para encontrar o utilizador
        when(utilizadorRepository.findById(userId)).thenReturn(java.util.Optional.of(utilizador));

        // Execução do método a ser testado
        notificacaoService.enviarNotificação(userId, mensagem);

        // Verificação se o método save foi chamado
        verify(notificacoesRepository, times(1)).save(any(Notificacoes.class));
    }

    @Test
    void testEnviarNotificacao_UtilizadorNaoEncontrado(){
        // Configuração do ID do utilizador e da mensagem
        Long userId = 1L;
        String mensagem = "Nova tarefa atribuída.";

        // Configuração do mock para não encontrar o utilizador
        when(utilizadorRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        // Execução e verificação da exceção
        Exception exception = assertThrows(IllegalArgumentException.class, ()->
                notificacaoService.enviarNotificação(userId, mensagem)
        );

        assertEquals("Utilizador não encontrado.", exception.getMessage());

        // Verificação que o método save não foi chamado
        verifyNoInteractions(notificacoesRepository);
    }

    @Test
    void testListarNotificaoes(){
        // Configuração do ID do utilizador
        Long userId = 1L;

        // Simulação de notificações na base de dados
        Utilizador utilizador = new Utilizador();
        utilizador.setId(userId);

        Notificacoes notificacao1 = new Notificacoes();
        notificacao1.setId(1L);
        notificacao1.setMensagem("Notificação 1");
        notificacao1.setDataEnvio(LocalDateTime.now());
        notificacao1.setUtilizador(utilizador);

        Notificacoes notificacao2 = new Notificacoes();
        notificacao2.setId(2L);
        notificacao2.setMensagem("Notificação 2");
        notificacao2.setDataEnvio(LocalDateTime.now());
        notificacao2.setUtilizador(utilizador);

        // Simulação do DTO esperado
        NotificacaoDto dto1 = new NotificacaoDto();
        dto1.setMensagem("Notificação 1");
        dto1.setDataEnvio(notificacao1.getDataEnvio());

        NotificacaoDto dto2 = new NotificacaoDto();
        dto2.setMensagem("Notificação 2");
        dto2.setDataEnvio(notificacao2.getDataEnvio());

        //Configuração dos mocks
        when(notificacoesRepository.findAll()).thenReturn(List.of(notificacao1, notificacao2));
        when(modelMapper.map(notificacao1, NotificacaoDto.class)).thenReturn(dto1);
        when(modelMapper.map(notificacao2, NotificacaoDto.class)).thenReturn(dto2);

        // Execução do método a ser testado
        List<NotificacaoDto> result = notificacaoService.listarNotificacoes(userId);

        // Verificações
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Notificação 1", result.get(0).getMensagem());
        assertEquals("Notificação 2", result.get(1).getMensagem());

        // Verificação das interações com os mocks
        verify(notificacoesRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(notificacao1, NotificacaoDto.class);
        verify(modelMapper, times(1)).map(notificacao2, NotificacaoDto.class);
    }
}
