package org.example.service;

import org.example.dto.RegistoLoginDto;
import org.example.models.RegistoLogins;
import org.example.models.Utilizador;
import org.example.repository.RegistoLoginRepository;
import org.example.repository.UtilizadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegistoLoginServiceTest {

    @InjectMocks
    private RegistoLoginService registoLoginService;

    @Mock
    private RegistoLoginRepository registoLoginRepository;

    @Mock
    private UtilizadorRepository utilizadorRepository;

    @Mock
    private ModelMapper modelMapper;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp(){
        //Inicia os mocks com try-with-resources
        mocks = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistarTentativaLogin_Sucesso(){
        // Configuração do ID do utilizador e status do login
        Long userId = 1L;
        Boolean statusLogin = true;

        // Simulação de um utilizador encontrado
        Utilizador utilizador = new Utilizador();
        utilizador.setId(userId);
        utilizador.setEmail("test@example.com");

        // Configuração do mock para encontrar o utilizador
        when(utilizadorRepository.findById(userId)).thenReturn(java.util.Optional.of(utilizador));

        // Execução do método a ser testado
        registoLoginService.registarTentativaLogin(userId, statusLogin);

        // Verificação se o método save foi chamado com a entidade correta
        verify(registoLoginRepository, times(1)).save(any(RegistoLogins.class));
    }

    @Test
    void testRegistarTentativaLogin_UtilizadorNaoEncontrado(){
        // Configuração do ID do utilizador e status do login
        Long userId = 1L;
        Boolean statusLogin = true;

        // Configuração do mock para não encontrar o utilizador
        when(utilizadorRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        // Execução e verificação da exceção
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                registoLoginService.registarTentativaLogin(userId, statusLogin)
        );

        assertEquals("Utilizador não encontrado.",exception.getMessage());

        //Verificação que o método save não foi chamado
        verifyNoInteractions(registoLoginRepository);
    }

    @Test
    void testConverterParaDto(){
        // Simulação de uma entidade RegistoLogins
        RegistoLogins registoLogins = new RegistoLogins();
        registoLogins.setId(1L);
        registoLogins.setStatusLogin(true);
        registoLogins.setDataLogin(LocalDateTime.now());

        // Simulação de um DTO esperado
        RegistoLoginDto expectedDto = new RegistoLoginDto();
        expectedDto.setLoginId(1L);
        expectedDto.setLoginStatus(true);

        // Configuração do mock para a conversão
        when(modelMapper.map(registoLogins, RegistoLoginDto.class)).thenReturn(expectedDto);

        // Execução do método a ser testado
        RegistoLoginDto result = registoLoginService.converterParaDto(registoLogins);

        // Verificações
        assertNotNull(result);
        assertEquals(expectedDto.getLoginId(),result.getLoginId());
        assertEquals(expectedDto.getStatusLogin(),result.getStatusLogin());

        // Verificação da interação com o modelMapper
        verify(modelMapper, times(1)).map(registoLogins, RegistoLoginDto.class);
    }
}
