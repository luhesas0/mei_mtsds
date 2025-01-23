package com.example.controller;

import com.example.dto.RegistoLoginDTO;
import com.example.models.RegistoLogin;
import com.example.service.RegistoLoginService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para gerir registos de login de utilizadores.
 */
@RestController
@RequestMapping("/utilizadores/registos-login")
public class RegistoLoginController {

    @Autowired
    private RegistoLoginService registoLoginService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Lista todos os registos de login.
     *
     * @return Lista de registos de login no formato DTO.
     */
    @GetMapping
    public ResponseEntity<List<RegistoLoginDTO>> getAllRegistosLogin() {
        List<RegistoLogin> registosLogin = registoLoginService.getAllRegistosLogin();
        return ResponseEntity.status(HttpStatus.OK).body(
                registosLogin.stream()
                        .map(r -> modelMapper.map(r, RegistoLoginDTO.class))
                        .collect(Collectors.toList())
        );
    }

    /**
     * Regista um novo login.
     *
     * @param registoLoginDTO Dados do registo de login no formato DTO.
     * @return O registo de login criado no formato DTO.
     */
    @PostMapping
    public ResponseEntity<RegistoLoginDTO> addRegistoLogin(@RequestBody RegistoLoginDTO registoLoginDTO){
            RegistoLogin registoLogin = modelMapper.map(registoLoginDTO, RegistoLogin.class);
            registoLogin = registoLoginService.addRegistoLogin(registoLogin);
            return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(registoLogin, RegistoLoginDTO.class));
    }
}
