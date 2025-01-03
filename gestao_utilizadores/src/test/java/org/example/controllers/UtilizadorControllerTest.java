package org.example.controllers;

import org.example.dto.CriarUtilizadorDto;
import org.example.dto.UtilizadorDto;
import org.example.service.UtilizadorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UtilizadorController.class)
public class UtilizadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UtilizadorService utilizadorService;

    private UtilizadorDto mockUserDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        mockUserDto = new UtilizadorDto();
        mockUserDto.setId(1L);
        mockUserDto.setNome("Teste Utilizador");
        mockUserDto.setEmail("teste@example.com");
        mockUserDto.setStatus(true);
    }

    @Test
    void testListarTodos() throws Exception{
        when(utilizadorService.listarUtilizadores()).thenReturn(Arrays.asList(mockUserDto));

        mockMvc.perform(get("/api/utilizadores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].nome").value("Teste Utilizador"));
    }

    @Test
    void testObterPorId() throws Exception{
        when(utilizadorService.obterUtilizadorPorId(1L)).thenReturn(Optional.of(mockUserDto));

        mockMvc.perform(get("/api/utilizadores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Teste Utilizador"));
    }

    @Test
    void testObterPorIs_NotFound() throws Exception{
        when(utilizadorService.obterUtilizadorPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/utilizadores/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Utilizador não encontrado."));
    }

    @Test
    void testCriarUtilizador() throws Exception{
        CriarUtilizadorDto criarDTO = new CriarUtilizadorDto();
        criarDTO.setNome("Novo Utilizador");
        criarDTO.setEmail("novo@example.com");
        criarDTO.setPassword("password");

        when(utilizadorService.criarUtilizador(any())).thenReturn(mockUserDto);

        mockMvc.perform(post("/api/utilizadores")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\": \"Novo Utilizador\",\"email\": \"novo@example.com\",\"password\": \"password\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Teste Utilizador"));
    }

    @Test
    void testCriarUtilizador_BadRequest() throws Exception{
        when(utilizadorService.criarUtilizador(any()))
                .thenThrow(new IllegalArgumentException("Email já em uso."));

        mockMvc.perform(post("/api/utilizadores")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\": \"Novo Utilizador\",\"email\":\"existente@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email já em uso."));
    }

    @Test
    void testExcluirUtilizador() throws Exception{
        mockMvc.perform(delete("/api/utilizadores/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testAtualizarUtilizador() throws Exception{
        when(utilizadorService.obterUtilizadorPorId(1L)).thenReturn(Optional.of(mockUserDto));
        when(utilizadorService.criarUtilizador(any())).thenReturn(mockUserDto);

        mockMvc.perform(put("/api/utilizadores/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\": \"Atualizado\",\"email\": \"atualizado@example.com\",\"password\": \"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Teste Utilizador"));
    }

    @Test
    void testAtualizarUtilizador_NotFound() throws Exception{
        when(utilizadorService.obterUtilizadorPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/utilizadores/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\": \"Atualizado\",\"email\": \"atualizado@example.com\",\"password\": \"password\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Utilizador não encontrado."));
    }
}
