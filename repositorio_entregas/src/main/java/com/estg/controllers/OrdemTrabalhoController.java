package com.estg.controllers;
import com.estg.dtos.OrdemTrabalhoDTO;
import com.estg.models.OrdemTrabalho;
import com.estg.service.OrdemTrabalhoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repositorio_entregas/ordens_trabalho")
public class OrdemTrabalhoController {

    private static final Logger logger = LoggerFactory.getLogger(OrdemTrabalhoController.class);

    @Autowired
    private OrdemTrabalhoService OtService;

    @PostMapping
    public ResponseEntity<OrdemTrabalhoDTO> createOT(@RequestBody OrdemTrabalhoDTO ordemEntregaDTO) {
        logger.info("Received new OT id: {}", ordemEntregaDTO.getOrder_id());
        OrdemTrabalhoDTO novaOrdem = OtService.addOrdemTrabalho(ordemEntregaDTO);
        logger.info("OT created with sucess: {}", novaOrdem.getOrder_id());
        return ResponseEntity.ok(novaOrdem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemTrabalhoDTO> getOT(@PathVariable Integer id) {
        logger.info("Buscando ordem de entrega com ID: {}", id);
        OrdemTrabalhoDTO ordem = OtService.getOrdemTrabalhoById(id);
        return ResponseEntity.ok(ordem);
    }

    @GetMapping
    public ResponseEntity<List<OrdemTrabalhoDTO>> listOTs() {
        logger.info("Listando todas as ordens de entrega");
        List<OrdemTrabalhoDTO> ordens = OtService.getAllOTs();
        return ResponseEntity.ok(ordens);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdemTrabalhoDTO> atualizarOrdem(@PathVariable Integer id, @RequestBody OrdemTrabalhoDTO ordemEntregaDTO) {
        logger.info("Atualizando ordem de entrega com ID: {}", id);
        OrdemTrabalhoDTO ordemAtualizada = OtService.updateOrdemTrabalho(id, ordemEntregaDTO);
        return ResponseEntity.ok(ordemAtualizada);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrdemTrabalhoDTO> atualizarStatus(@PathVariable Integer id, @RequestBody OrdemTrabalhoDTO ordemEntregaDTO) {
        logger.info("Atualizando status da ordem de entrega com ID: {}", id);
        OrdemTrabalhoDTO ordemAtualizada = OtService.updateOTStatus(id, ordemEntregaDTO.getStatus());
        return ResponseEntity.ok(ordemAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarOrdem(@PathVariable Integer id) {
        logger.info("Ordem de trabalho removida com` ID: {}", id);
        OtService.deleteOrdemTrabalho(id);
        return ResponseEntity.noContent().build();
    }

}
