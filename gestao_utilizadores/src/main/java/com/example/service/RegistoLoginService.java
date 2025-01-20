package com.example.service;

import com.example.models.RegistoLogin;
import com.example.repository.RegistoLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço para operações relacionadas aos registos de logins.
 */
@Service
public class RegistoLoginService {

    @Autowired
    private RegistoLoginRepository registoLoginRepository;

    /**
     * Recupera todos os registos de login.
     *
     * @return lista de registos de login.
     */
    public List<RegistoLogin> getAllRegistosLogin(){
        return registoLoginRepository.findAll();
    }

    /**
     * Adiciona um novo registo de login.
     *
     * @param registoLogin dados do registo de login a ser adicionado.
     * @return registo de login adicionado.
     */
    public RegistoLogin addRegistoLogin(RegistoLogin registoLogin){
        return registoLoginRepository.save(registoLogin);
    }
}
