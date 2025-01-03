package org.example.service;

import org.example.dto.CriarUtilizadorDto;
import org.example.dto.LoginDto;
import org.example.dto.LoginResponseDto;
import org.example.dto.UtilizadorDto;
import org.example.models.Utilizador;
import org.example.repository.UtilizadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pela autenticação de utilizadores.
 */
@Service
public class AuthService {

    @Autowired
    private UtilizadorRepository utilizadorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Realiza autenticação de utilizadores.
     *
     * @param loginDto Dados de Login (email e password).
     * @return DTO com o token JWT.
     */
    public LoginResponseDto autenticar(LoginDto loginDto){
        //Busca o utilizador pelo email
        UserDetails userDetails = utilizadorRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email ou password inválidos."));

        // Converte UserDetails para utilizador (casting explícito)
        Utilizador utilizador = (Utilizador) userDetails;

           // Verifica se a password é válida
        if (!passwordEncoder.matches(loginDto.getPassword(), utilizador.getPassword())){
            throw new IllegalArgumentException("Email ou password inválidos.");
        }

        // Gera o token JWT
        String token = tokenService.generateToken(utilizador);

        // Prepara a resposta com o token gerado
        LoginResponseDto response = new LoginResponseDto();
        response.setToken(token);
        return response;
    }

    public UtilizadorDto registerUser(CriarUtilizadorDto criarUtilizadorDto){
        if (utilizadorRepository.existsByEmail(criarUtilizadorDto.getEmail())){
            throw new IllegalArgumentException("Email já está em uso.");
        }

        Utilizador utilizador = modelMapper.map(criarUtilizadorDto,Utilizador.class);
        utilizador.setPassword(passwordEncoder.encode(criarUtilizadorDto.getPassword()));
        utilizador.setStatus(true);

        Utilizador savedUser = utilizadorRepository.save(utilizador);
        return modelMapper.map(savedUser, UtilizadorDto.class);
    }
}
