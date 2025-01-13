package org.example.controllers;

import org.example.dto.VeiculoAtribuicaoDTO;
import org.example.models.Veiculo;
import org.example.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável por gerir as operações de veículos.
 */
@RestController
@RequestMapping("/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    /**
     * Endpoint para atribuir um veículo a um funcionário.
     *
     * @param veiculoAtribuicaoDTO Objeto com os dados necessários para a atribuição do veículo.
     * @return Resposta confirmando a atribuição.
     */
    @PostMapping("/atribuir")
    public ResponseEntity<String> atribuirVeiculo(@RequestBody VeiculoAtribuicaoDTO veiculoAtribuicaoDTO){
        try{
            veiculoService.atribuirVeiculo(veiculoAtribuicaoDTO);
            return ResponseEntity.ok("Veículo atribuído com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para consultar os veículos atribuídos a um funcionário.
     *
     * @param funcionarioId ID do funcionário cujos veículos serão consultados.
     * @return Lista de veículos atribuidos ao funcionário.
     */
    @GetMapping("/funcionario/{id}")
    public ResponseEntity<List<Veiculo>> consultarVeiculosFuncionario(@PathVariable("id") Long funcionarioId){
        try {
            List<Veiculo> veiculos = veiculoService.consultarVeiculosFuncionario(funcionarioId);
            return ResponseEntity.ok(veiculos);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(null);
        }
    }
}
