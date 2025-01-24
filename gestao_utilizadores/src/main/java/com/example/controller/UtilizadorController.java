package com.example.controller;

import com.example.dto.UtilizadorDTO;
import com.example.exceptions.UtilizadorExistente;
import com.example.exceptions.UtilizadorNaoExiste;
import com.example.models.Utilizador;
import com.example.service.UtilizadorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para gerir operações relacionadas aos utilizadores.
 */
@RestController
@RequestMapping("/utilizadores")
public class UtilizadorController {

    @Autowired
    private UtilizadorService utilizadorService;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     *  Endpoint para verificar se o serviço de utilizadores está operacional.
     *
     * @return Uma mensagem indicando sucesso na ligação ao serviço.
     */
    @GetMapping("/landing")
    public ResponseEntity<String> landing(){
        return ResponseEntity.status(HttpStatus.OK).body("Utilizadores service está em sucesso.");
    }

    /**
     * Obtém a lista de todos os utilizadores.
     *
     * @return Lista de utilizadores no formato DTO.
     */
    @GetMapping
    public ResponseEntity<List<UtilizadorDTO>> getAll() {
        List<Utilizador> utilizadores = utilizadorService.getAllUtilizadores();
        return ResponseEntity.status(HttpStatus.OK).body(
                utilizadores.stream()
                        .map(u -> modelMapper.map(u, UtilizadorDTO.class))
                        .collect(Collectors.toList())
        );
    }

    /**
     * Obtém um utilizador específico pelo ID.
     *
     * @param id O ID do utilizadore a ser recuperado.
     * @return O utilizador correspondente no formato DTO ou mensagem de erro se não encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id){
        try{
            Utilizador utilizador = utilizadorService.getUtilizadorById(id);
            return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(utilizador, UtilizadorDTO.class));
        } catch (UtilizadorNaoExiste e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Cria um novo utilizador.
     *
     * @param utilizadorDTO Os dados do utilizador no formato DTO.
     * @return O utilizador criado no formato DTO ou mensagem de erro se já existir.
     */
    @PostMapping
    public ResponseEntity<?> add(@RequestBody UtilizadorDTO utilizadorDTO){
        try{
            Utilizador utilizador = modelMapper.map(utilizadorDTO, Utilizador.class);
            utilizador = utilizadorService.createUtilizador(utilizador);
            return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(utilizador, UtilizadorDTO.class));
        } catch (UtilizadorExistente e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um utilizador existente.
     *
     * @param id O ID do utilizador a ser atualizado.
     * @param utilizadorDTO Os novos dados do utilizador no formato DTO.
     * @return O utilizador atualizado no formato DTO ou mensagem de erro se não encontrado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody UtilizadorDTO utilizadorDTO){
        try{
            Utilizador utilizador = modelMapper.map(utilizadorDTO, Utilizador.class);
            utilizador.setId(Long.parseLong(id));
            utilizador = utilizadorService.updateUtilizador(utilizador);
            return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(utilizador, UtilizadorDTO.class));
        } catch (UtilizadorNaoExiste e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Exclui um utilizador pelo ID.
     *
     * @param id O ID do utilizador a ser excluído.
     * @return Resposta sem conteúdo (204) ou mensagem de erro se não encontrado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        try{
            utilizadorService.deleteUtilizador(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (UtilizadorNaoExiste e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Endpoint para verificar se um administrador pode aceder ao serviço.
     *
     * @return Mensagem confirmando o acesso administrativo.
     */
    @GetMapping("/adminping")
    public ResponseEntity<String> adminPing(){
        return ResponseEntity.status(HttpStatus.OK).body("Acesso de administrador verificado.");
    }

    /**
     * Endpoint para verificar se um utilizador autenticado pode aceder ao serviço.
     */
    @GetMapping("/userping")
    public ResponseEntity<String> userPing(){
        return ResponseEntity.status(HttpStatus.OK).body("Acesso do utilizador verificado.");
    }
}
