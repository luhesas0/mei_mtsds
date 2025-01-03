package org.example.controllers;

import jakarta.validation.Valid;
import org.example.dto.CriarUtilizadorDto;
import org.example.dto.UtilizadorDto;
import org.example.service.UtilizadorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilizadores")
public class UtilizadorController {

    private final UtilizadorService utilizadorService;

    public UtilizadorController(UtilizadorService utilizadorService){
        this.utilizadorService = utilizadorService;
    }

    /**
     * Listar todos os utilizadores(apenas administradores).
     * @return Lista de utilizadores.
     */
    @GetMapping
    public ResponseEntity<List<UtilizadorDto>> listarTodos(){
        List<UtilizadorDto> utilizadores = utilizadorService.listarUtilizadores();
        return ResponseEntity.ok(utilizadores);
    }

    /**
     * Obter informações de um utilizador específico.
     * @param id ID do utilizador
     * @return Detalhes do utilizador.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obterPorID(@PathVariable Long id){
        return utilizadorService.obterUtilizadorPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Utilizador não encontrado."));
    }
    
    /**
     * Criar um novo utilizador (apenas administradores).
     * @param criarUtilizadorDto Dados do utilizador.
     * @return Utilizador criado.
     */
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid CriarUtilizadorDto criarUtilizadorDto){
        try {
            UtilizadorDto novoUtilizador = utilizadorService.criarUtilizador(criarUtilizadorDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUtilizador);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Atualizar perfil ou permissões de um utilizador.
     * @param id ID do utilizador.
     * @param criarUtilizadorDto Novos dados do utilizador.
     * @return Utilizador atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid CriarUtilizadorDto criarUtilizadorDto){
        return utilizadorService.obterUtilizadorPorId(id).<ResponseEntity<?>>map(utilizador ->{
            try{
                criarUtilizadorDto.setId(id); //Atualiza com o ID existente.
                UtilizadorDto utilizadorAtualizado = utilizadorService.criarUtilizador(criarUtilizadorDto);
                return ResponseEntity.ok(utilizadorAtualizado);
            } catch (IllegalArgumentException e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilizador não encontrado."));
    }

    /**
     * Excluir um utilizador (apenas administradores).
     * @param id ID do utilizador.
     * @return Resposta de exclusão.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id){
        try{
            utilizadorService.excluirUtilizador(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Obter informações do utilizador autenticado.
     * @param utilizador Utilizador autenticado.
     * @return Informações do utilizador.
     */
    @GetMapping("/me")
    public ResponseEntity<?> obterMeuPerfil(@AuthenticationPrincipal org.example.models.Utilizador utilizador){
        if(utilizador == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não autenticado.");
        }
        UtilizadorDto utilizadorDto = new UtilizadorDto();
        utilizadorDto.setId(utilizador.getId());
        utilizadorDto.setEmail(utilizador.getEmail());
        utilizadorDto.setNome(utilizador.getNome());
        utilizadorDto.setStatus(utilizador.getStatus());
        return ResponseEntity.ok(utilizadorDto);
    }
}
