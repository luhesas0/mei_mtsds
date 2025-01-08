package org.example.utils;

import org.example.dto.RoleDTO;
import org.example.models.UtilizadorRule;

/**
 * Classe utilitária para mapear UtilizadorRule para RoleDTO.
 */
public class RoleMapper {

    /**
     * Mapeia uma enum UtilizadorRule para um DTO RoleDTO.
     *
     * @param utilizadorRule A enumeração UtilizadorRule.
     * @return RoleDTO correspondente.
     */
    public static RoleDTO mapToRoleDTO(UtilizadorRule utilizadorRule){
        if (utilizadorRule==null){
            return null; //retorna nulo se a entrada for nula.
        }

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setNome(utilizadorRule.name());//Nome do papel (ADMIN,FUNCIONARIO,CLIENTE).
        roleDTO.setDescricao(utilizadorRule.getRole());//Descrição amigável("Administrador").
        return roleDTO;
    }
}
