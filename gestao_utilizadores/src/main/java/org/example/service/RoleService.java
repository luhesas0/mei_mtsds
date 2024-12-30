package org.example.service;

import org.example.dto.RoleDto;
import org.example.models.Role;
import org.example.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Busca uma role pelo nome.
     * @param nome Nome da role.
     * @return DTO da role encontrada.
     */
    public RoleDto buscarRolePorNome(String nome){
        Optional<Role> role = roleRepository.findByNome(nome);
        return role.map(r->modelMapper.map(r, RoleDto.class))
                .orElseThrow(()-> new IllegalArgumentException("Role não encontrada."));
    }
}
