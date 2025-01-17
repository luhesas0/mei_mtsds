package com.example.service;

import com.example.repository.UtilizadorRepository;
import com.example.exceptions.UtilizadorExistente;
import com.example.exceptions.UtilizadorNaoExiste;
import com.example.models.Utilizador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço para operações relacionadas aos utilizadores.
 */
@Service
public class UtilizadorService implements UserDetailsService {

    @Autowired
    private UtilizadorRepository utilizadorRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     *  Recupera todos os utilizadores.
     *
     * @return Lista de utilizadores.
     */
    public List<Utilizador> getAllUtilizadores(){
        return utilizadorRepository.findAll();
    }

    /**
     * Recupera um utilizador pelo ID.
     *
     * @param id ID do utilizador.
     * @return O utilizador encontrado.
     * @throws UtilizadorNaoExiste se o utilizador não for encontrado.
     */
    public Utilizador getUtilizadorById(String id) throws UtilizadorNaoExiste{
        return utilizadorRepository.findById(id)
                .orElseThrow(()-> new UtilizadorNaoExiste("Utilizador com ID" + id + "não foi encontrado."));
    }

    /**
     * Recupera os detalhes de um utilizador pelo email.
     *
     * @param email Email do utilizador.
     * @return UserDetails do utilizador.
     * @throws UtilizadorNaoExiste se o utilizador não for encontrado.
     */
    public UserDetails getUtilizadorByEmail(String email) throws UtilizadorNaoExiste{
        UserDetails utilizador = utilizadorRepository.findByEmail(email);
        if(utilizador == null){
            throw new UtilizadorNaoExiste("Utilizador com email" + email + "não foi encontrado.");
        }
        return utilizador;
    }

    /**
     * Implementa o método da interface UserDetailsService.
     *
     * @param username Email do utilizador.
     * @return UserDetails do utilizador.
     * @throws UsernameNotFoundException se o utilizador não for encontrado.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        try{
            return getUtilizadorByEmail(username);
        } catch (UtilizadorNaoExiste e){
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    /**
     * Cria um novo utilizador.
     *
     * @param utilizador Dados do utilizador a serem criados.
     * @return Utilizador criado.
     * @throws UtilizadorExistente se o email do utilizador já existir.
     */
    public Utilizador createUtilizador(Utilizador utilizador) throws UtilizadorExistente{
        if(utilizadorRepository.existsByEmail(utilizador.getEmail())){
            throw new UtilizadorExistente(utilizador.getEmail());
        }

        // Criptografa a password antes de salvar
        utilizador.setPassword(passwordEncoder.encode(utilizador.getPassword()));

        return utilizadorRepository.save(utilizador);
    }

    /**
     * Atualiza os dados de um utilizador existente.
     *
     * @param utilizador Dados do utilizador a serem atualizados.
     * @return Utilizador atualizado.
     * @throws UtilizadorNaoExiste Se o utilizador não for encontrado.
     */
    public Utilizador updateUtilizador(Utilizador utilizador) throws UtilizadorNaoExiste{
        if (!utilizadorRepository.existsById(utilizador.getId().toString())){
            throw new UtilizadorNaoExiste("Utilizador com ID" + utilizador.getId() + "não foi encontrado.");
        }
        return utilizadorRepository.save(utilizador);
    }

    /**
     * Exclui um utilizador pelo ID.
     *
     * @param id ID do utilizador.
     * throws UtilizadorNaoExiste Se o utilizador não for encontrado.
     */
    public void deleteUtilizador(String id) throws UtilizadorNaoExiste{
        if (!utilizadorRepository.existsById(id)){
            throw new UtilizadorNaoExiste("Utilizador com ID" + id + "não foi encontrado.");
        }
        utilizadorRepository.deleteById(id);
    }
}
