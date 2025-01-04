package org.example.service;

import org.example.dto.RoleDto;
import org.example.models.Role;
import org.example.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Criar uma nova role.
     * @param roleDto Dados da nova role.
     * @return Role criada.
     */
    public Role criarRole(RoleDto roleDto){
        if(roleRepository.findByNome(roleDto.getNome()).isPresent()){
            throw new IllegalArgumentException("Role já existe.");
        }
        Role novaRole = modelMapper.map(roleDto, Role.class);
        return roleRepository.save(novaRole);
    }

    /**
     * Listar todas as roles.
     * @return Lista de roles.
     */
    public List<RoleDto> listarRoles(){
        return roleRepository.findAll().stream()
                .map(role -> modelMapper.map(role, RoleDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Atualizar uma role existente.
     * @param id ID da role.
     * @param roleDto Dados atualizados.
     * @return Role atualizada.
     */
    public Role atualizarRole(Long id, RoleDto roleDto){
        Optional<Role> roleExistente = roleRepository.findById(id);
        if (roleExistente.isEmpty()){
            throw new IllegalArgumentException("Role com ID" + id+ "não encontrada.");
        }

        Role roleAtualizada = roleExistente.get();
        roleAtualizada.setNome(roleDto.getNome());
        return roleRepository.save(roleAtualizada);
    }
}
