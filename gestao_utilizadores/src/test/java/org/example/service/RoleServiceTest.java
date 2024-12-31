package org.example.service;

import org.example.dto.RoleDto;
import org.example.models.Role;
import org.example.models.UtilizadorRule;
import org.example.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp(){
        //Usando try-with-resources para iniciar mocks
        try (AutoCloseable mocks = MockitoAnnotations.openMocks(this)){
            // Mocks iniciados
        } catch (Exception e){
            throw new RuntimeException("Erro ao iniciar mocks", e);
        }
    }

    @Test
    void testBuscarRolePorNome(){
        // Configuração do nome da role
        String roleName = UtilizadorRule.ADMIN.getRole();

        // Configuração do mock para a entidade Role
        Role role = new Role();
        role.setId(1L);
        role.setNome(roleName);

        // Configuração do mock para o DTO de resposta
        RoleDto expectedDto = new RoleDto();
        expectedDto.setId(1L);
        expectedDto.setNome(roleName);

        // Configuração dos mocks
        when(roleRepository.findByNome(roleName)).thenReturn(Optional.of(role));
        when(modelMapper.map(role, RoleDto.class)).thenReturn(expectedDto);

        // Execução do método a ser testado
        RoleDto result = roleService.buscarRolePorNome(roleName);

        // Verificações
        assertNotNull(result);
        assertEquals(roleName, result.getNome());

        // Verifica interações com os mocks
        verify(roleRepository, times(1)).findByNome(roleName);
        verify(modelMapper, times(1)).map(role, RoleDto.class);
    }

    @Test
    void testBuscarRolePorNomeNotFound(){
        // Configuração do nome da role
        String roleName = UtilizadorRule.CLIENTE.getRole();

        // Configuração do mock para role não encontrada
        when(roleRepository.findByNome(roleName)).thenReturn(Optional.empty());

        // Execução e verificação da exceção
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                roleService.buscarRolePorNome(roleName)
        );

        assertEquals("Role não encontrada.", exception.getMessage());

        // Verifica interações com os mocks
        verify(roleRepository, times(1)).findByNome(roleName);
        verifyNoInteractions(modelMapper);
    }
}
