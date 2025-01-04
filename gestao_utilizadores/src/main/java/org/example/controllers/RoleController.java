package org.example.controllers;

import jakarta.validation.Valid;
import org.example.dto.RoleDto;
import org.example.models.Role;
import org.example.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Responsável por gerir permissões (roles no sistema).
 *
 */
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService){
        this.roleService=roleService;
    }

    /**
     * Criar uma nova permissão (role) no sistema.
     * @param roleDto Dados da nova role.
     * @return Role criada ou erro de validação.
     */
    @PostMapping
    public ResponseEntity<?> criarRole(@RequestBody @Valid RoleDto roleDto){
        try {
            Role roleCriada = roleService.criarRole(roleDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(roleCriada);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Listar todas as permissões existentes no sistema.
     * @return Lista de roles.
     */
    @GetMapping
    public ResponseEntity<List<RoleDto>> listarRoles(){
        List<RoleDto> roles = roleService.listarRoles();
        return ResponseEntity.ok(roles);
    }

    /**
     * Atualizar permissões de um utilizador (administrador).
     * @param id ID da role.
     * @param roleDto Dados atualizados da role.
     * @return Role atualizada ou erro.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarRole(@PathVariable Long id, @RequestBody @Valid RoleDto roleDto){
        try{
            Role roleAtualizada = roleService.atualizarRole(id, roleDto);
            return ResponseEntity.ok(roleAtualizada);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
