package com.example.service;

import com.example.exceptions.PermissaoNaoEncontrada;
import com.example.models.Permissao;
import com.example.repository.PermissaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PermissaoServiceTest {

    @InjectMocks
    private PermissaoService permissaoService;

    @Mock
    private PermissaoRepository permissaoRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPermissaoByIdSucess() throws PermissaoNaoEncontrada {
        Integer id = 1;
        Permissao permissao = new Permissao();
        permissao.setId(id);

        when(permissaoRepository.findById(id)).thenReturn(Optional.of(permissao));

        Permissao result = permissaoService.getPermissaoById(id);
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(permissaoRepository, times(1)).findById(id);

        // Mensagem de sucesso
        System.out.println("Teste 'getPermissaoByIdSucess' executado com sucesso.");
    }

    @Test
    void getPermissaoByIdThrowsPermissaoNaoEncontrada(){
        Integer id = 1;
        when(permissaoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PermissaoNaoEncontrada.class, ()-> permissaoService.getPermissaoById(id));

        // Mensagem de sucesso
        System.out.println("Teste 'getPermissaoByIdThrowsPermissaoNaoEncontrada' executado com sucesso.");
    }
}
