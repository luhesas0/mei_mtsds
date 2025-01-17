package com.example.service;

import com.example.models.RegistoLogin;
import com.example.repository.RegistoLoginRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RegistoLoginServiceTest {

    @InjectMocks
    private RegistoLoginService registoLoginService;

    @Mock
    private RegistoLoginRepository registoLoginRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRegistosLoginSucess(){
        List<RegistoLogin> registos = List.of(new RegistoLogin(), new RegistoLogin());
        when(registoLoginRepository.findAll()).thenReturn(registos);

        List<RegistoLogin> result = registoLoginService.getAllRegistosLogin();
        assertEquals(2, result.size());
        verify(registoLoginRepository, times(1)).findAll();

        // Mensagem de sucesso
        System.out.println("Teste 'getAllRegistosLoginSucess' com Sucesso!");
    }

    @Test
    void addRegistoLoginSuccess(){
        RegistoLogin registo = new RegistoLogin();
        when(registoLoginRepository.save(registo)).thenReturn(registo);

        RegistoLogin result = registoLoginService.addRegistoLogin(registo);
        assertEquals(registo, result);
        verify(registoLoginRepository, times(1)).save(registo);

        // Mensagem de sucesso
        System.out.println("Teste 'addRegistoLoginSuccess' com Sucesso!");
    }
}
