package com.example.service;

import com.example.models.Utilizador;
import com.example.repository.UtilizadorRepository;
import com.example.exceptions.UtilizadorExistente;
import com.example.exceptions.UtilizadorNaoExiste;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UtilizadorServiceTest {

    @InjectMocks
    private UtilizadorService utilizadorService;

    @Mock
    private UtilizadorRepository utilizadorRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUtilizadorSuccess() throws UtilizadorExistente{
        Utilizador utilizador = new Utilizador();
        utilizador.setId(1L);
        utilizador.setEmail("test@example.com");
        utilizador.setPassword("password");

        when(utilizadorRepository.existsByEmail(utilizador.getEmail())).thenReturn(false);
        when(utilizadorRepository.save(utilizador)).thenReturn(utilizador);

        Utilizador result = utilizadorService.createUtilizador(utilizador);
        assertNotNull(result);
        assertEquals("test@example.com",result.getEmail());
        verify(utilizadorRepository, times(1)).save(utilizador);

        //Mensagem com Sucesso
        System.out.println("Teste 'createUtilizadorSuccess' com Sucesso!");
    }

    @Test
    void createUtilizadorThrowsUtilizadorExistente(){
        Utilizador utilizador = new Utilizador();
        utilizador.setEmail("test@example.com");

        when(utilizadorRepository.existsByEmail(utilizador.getEmail())).thenReturn(true);

        assertThrows(UtilizadorExistente.class, ()-> utilizadorService.createUtilizador(utilizador));

        // Mensagem de sucesso
        System.out.println("Teste 'createUtilizadorThrowsUtilizadorExistente' com Sucesso!");
    }

    @Test
    void deleteUtilizadorSucess() throws UtilizadorNaoExiste{
        String id = "1";
        when(utilizadorRepository.existsById(id)).thenReturn(true);
        doNothing().when(utilizadorRepository).deleteById(id);

        utilizadorService.deleteUtilizador(id);
        verify(utilizadorRepository, times(1)).deleteById(id);

        System.out.println("Teste 'deleteUtilizadorSucess' com Sucesso!");
    }

    @Test
    void deleteUtilizadorThrowsUtilizadorNaoExiste(){
        String id = "1";
        when(utilizadorRepository.existsById(id)).thenReturn(false);

        assertThrows(UtilizadorNaoExiste.class, ()-> utilizadorService.deleteUtilizador(id));

        // Mensagem de sucesso
        System.out.println("Teste 'deleteUtilizadorThrowsUtilizadorNaoExiste' com sucesso!");
    }

    @Test
    void getUtilizadorByEmailSuccess() throws UtilizadorNaoExiste{
        String email = "test@example.com";
        UserDetails userDetails = mock(UserDetails.class);
        when(utilizadorRepository.findByEmail(email)).thenReturn(userDetails);

        UserDetails result = utilizadorService.getUtilizadorByEmail(email);
        assertNotNull(result);
        verify(utilizadorRepository, times(1)).findByEmail(email);

        // Mensagem de sucesso
        System.out.println("Teste 'getUtilizadorByEmailSuccess' com sucesso!");
   }

   @Test
    void getUtilizadorByEmailThrowsUtilizadorNaoExiste(){
        String email = "test@example.com";
        when(utilizadorRepository.findByEmail(email)).thenReturn(null);

        assertThrows(UtilizadorNaoExiste.class, ()-> utilizadorService.getUtilizadorByEmail(email));

       // Mensagem de sucesso
       System.out.println("Teste 'getUtilizadorByEmailThrowsUtilizadorNaoExiste' com sucesso!");
   }
}
