package org.example.service;

import org.example.dto.CriarUtilizadorDto;
import org.example.dto.UtilizadorDto;
import org.example.models.Utilizador;
import org.example.repository.UtilizadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço para gestão de utilizadores no sistema.
 * Contém métodos para criar, listar, buscar e excluir utilizadores.
 */

@Service
public class UtilizadorService {

    @Autowired
    private UtilizadorRepository utilizadorRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Cria um novo utilizador no sistema.
     * @param dto Dados para criação do utilizador.
     * @return DTO do utilizador criado.
     */
    public UtilizadorDto criarUtilizador(CriarUtilizadorDto dto){
        if (utilizadorRepository.existsByEmail(dto.getEmail())){
            throw new IllegalArgumentException("Email já em uso.");
        }

        //Mapeia DTO para entidade
        Utilizador utilizador = modelMapper.map(dto, Utilizador.class);
        utilizador.setPassword(passwordEncoder.encode(dto.getPassword()));
        utilizador.setStatus(true); //Padrão ativo

        // Salva na base de dados
        utilizador = utilizadorRepository.save(utilizador);

        //Retorna o DTO do utilizador criado
        return modelMapper.map(utilizador, UtilizadorDto.class);
    }

    /**
     * Lista todos os utilizadores no sistema.
     * @return Lista de DTOs de utilizadores.
     */

    public List<UtilizadorDto> listarUtilizadores(){
        return utilizadorRepository.findAll().stream()
                .map(utilizador -> modelMapper.map(utilizador, UtilizadorDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Obtém um utilizador pelo seu ID.
     * @param id ID do utilizador
     * @return Um Optional contendo o DTO do utilizador, se encontrado.
     */
    public Optional<UtilizadorDto> obterUtilizadorPorId(Long id){
        //Busca o utilizador pelo ID
        return utilizadorRepository.findById(id)
                .map(utilizador -> modelMapper.map(utilizador, UtilizadorDto.class));
    }

    /**
     * Exclui um utilizador pelo seu ID.
     * @param id ID do utilizador a ser excluído.
     * @throws IllegalArgumentException se o utilizador não for encontrado.
     */
    public void excluirUtilizador(Long id){
        //Verifica se o utilizador existe antes de tentar excluir
        if(!utilizadorRepository.existsById(id)){
            throw new IllegalArgumentException("Utilizador não encontrado.");
        }

        //Exclui o utilizador
        utilizadorRepository.deleteById(id);
    }
}
