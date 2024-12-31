package org.example.service;

import org.example.dto.CriarUtilizadorDto;
import org.example.dto.UtilizadorDto;
import org.example.models.Utilizador;
import org.example.repository.UtilizadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UtilizadorServiceTest {

    @InjectMocks
    private UtilizadorService utilizadorService;

    @Mock
    private UtilizadorRepository utilizadorRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp(){
        //Usando try-with-resources para iniciar mocks de forma mais limpa
        try(AutoCloseable mocks = MockitoAnnotations.openMocks(this)){
            //Mocks foram iniciados
        } catch (Exception e){
            throw new RuntimeException("Erro ao iniciar mocks", e);
        }
    }

    @Test
    void testCriarUtilizador(){
        // Criação do DTO de entrada
        CriarUtilizadorDto dto = new CriarUtilizadorDto();
        dto.setEmail("test@example.com");
        dto.setPassword("password");

        // Criação de entidades simuladas
        Utilizador utilizador = new Utilizador();
        utilizador.setEmail(dto.getEmail());
        utilizador.setPassword("encodedPassword");

        Utilizador savedUtilizador = new Utilizador();
        savedUtilizador.setId(1L);
        savedUtilizador.setEmail("test@example.com");

        // DTO esperado como resposta
        UtilizadorDto expectedDto = new UtilizadorDto();
        expectedDto.setEmail("test@example.com");

        // Configuração dos mocks
        when(utilizadorRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(modelMapper.map(dto,Utilizador.class)).thenReturn(utilizador);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");
        when(utilizadorRepository.save(any(Utilizador.class))).thenReturn(savedUtilizador);
        when(modelMapper.map(savedUtilizador,UtilizadorDto.class)).thenReturn(expectedDto);

        // Execução do método a ser testado
        UtilizadorDto result = utilizadorService.criarUtilizador(dto);

        // Verificações
        assertNotNull(result);
        assertEquals("test@example.com",result.getEmail());

        // Verifica interações com os mocks
        verify(utilizadorRepository, times(1)).existsByEmail(dto.getEmail());
        verify(passwordEncoder, times(1)).encode(dto.getPassword());
        verify(utilizadorRepository, times(1)).save(any(Utilizador.class));
        verify(modelMapper, times(1)).map(savedUtilizador, UtilizadorDto.class);
    }
}
