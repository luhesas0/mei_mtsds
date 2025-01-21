package com.example.controllers;

import com.example.dto.PermissaoDTO;
import com.example.exceptions.PermissaoNaoEncontrada;
import com.example.models.Permissao;
import com.example.service.PermissaoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  Controlador REST para gerir permissões atribuídas aos utilizadores.
 */
@RestController
@RequestMapping("/utilizadores/permissoes")
public class PermissaoController {

    @Autowired
    private PermissaoService permissaoService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Lista todas as permissões disponíveis.
     */
    @GetMapping
    public ResponseEntity<List<PermissaoDTO>> getAllPermissoes(){
        List<Permissao> permissoes = permissaoService.getAllPermissoes();
        return ResponseEntity.status(HttpStatus.OK).body(
                permissoes.stream()
                        .map(p -> modelMapper.map(p, PermissaoDTO.class))
                        .collect(Collectors.toList())
        );
    }

    /**
     * Obtém os detalhes de uma permissão específica pelo ID.
     *
     * @param id ID da permissão a ser recuperada.
     * @return Detalhes da permissão no formato DTO ou mensagem de erro.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPermissaoById(@PathVariable Integer id){
        try {
            Permissao permissao = permissaoService.getPermissaoById(id);
            return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(permissao, PermissaoDTO.class));
        } catch (PermissaoNaoEncontrada e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Cria uma nova permissão.
     *
     * @param permissaoDTO Dados da permissão no formato DTO.
     * @return A permissao criada no formato DTO.
     */
    @PostMapping
    public ResponseEntity<PermissaoDTO> createPermissao(@RequestBody PermissaoDTO permissaoDTO){
        Permissao permissao = modelMapper.map(permissaoDTO, Permissao.class);
        permissao = permissaoService.createPermissao(permissao);
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(permissao, PermissaoDTO.class));
    }
}
