package com.example.service;

import com.example.exceptions.PermissaoNaoEncontrada;
import com.example.models.Permissao;
import com.example.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço para operações relacionadas às permissões.
 */
@Service
public class PermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    /**
     * Recupera todas as permissões.
     *
     * @return lista de permissões.
     */
    public List<Permissao> getAllPermissoes(){
        return permissaoRepository.findAll();
    }

    /**
     * Recupera uma permissão pelo ID.
     *
     * @param id ID da permissão.
     * @return permissão encontrada.
     * @throws PermissaoNaoEncontrada se a permissão não for encontrada.
     */
    public Permissao getPermissaoById(Integer id) throws PermissaoNaoEncontrada{
        return permissaoRepository.findById(id)
                .orElseThrow(()-> new PermissaoNaoEncontrada("Permissão com ID" + id));
    }

    /**
     * Cria uma nova permissão.
     *
     * @param permissao dados da permissão a ser criada.
     * @return permissao criada.
     */
    public Permissao createPermissao(Permissao permissao){
        return permissaoRepository.save(permissao);
    }
}
