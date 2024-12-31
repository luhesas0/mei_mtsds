package org.example.service;

import org.example.dto.RegistoLoginDto;
import org.example.models.RegistoLogins;
import org.example.models.Utilizador;
import org.example.repository.RegistoLoginRepository;
import org.example.repository.UtilizadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistoLoginsService {

    @Autowired
    private RegistoLoginRepository registoLoginRepository;

    @Autowired
    private UtilizadorRepository utilizadorRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Regista uma tentativa de login.
     * @param userId ID do utilizador.
     * @param statusLogin Status do login (sucesso ou falha).
     */
    public void registarTentativaLogin(Long userId, Boolean statusLogin){
        Utilizador utilizador = utilizadorRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado."));

        RegistoLogins registo = new RegistoLogins();
        registo.setUtilizador(utilizador);
        registo.setStatusLogin(statusLogin);
        registo.setDataLogin(LocalDateTime.now());
        registoLoginRepository.save(registo);
    }

    /**
     * Converte uma entidade de registo de Login para DTO.
     * @param registoLogin Registo de login.
     * @return DTO correspondente.
     */
    public RegistoLoginDto converterParaDto(RegistoLogins registoLogin){
        return modelMapper.map(registoLogin, RegistoLoginDto.class);
    }
}
