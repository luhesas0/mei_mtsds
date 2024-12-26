package org.example.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilizadorDTOTest {

    @Test
    void testUtilizadorDTO(){
        UtilizadorDTO utilizadorDTO = new UtilizadorDTO();
        utilizadorDTO.setId("123");
        utilizadorDTO.setNome("Teste");
        utilizadorDTO.setRoles(Arrays.asList("ADMIN","USER"));

        assertEquals("123", utilizadorDTO.getId());
        assertEquals("Teste", utilizadorDTO.getNome());
        assertEquals(List.of("ADMIN","USER"),utilizadorDTO.getRoles());
    }
}
