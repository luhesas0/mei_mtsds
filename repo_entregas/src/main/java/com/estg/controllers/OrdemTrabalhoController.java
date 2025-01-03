package com.estg.controllers;
import com.estg.dtos.OrdemTrabalhoDTO;
import com.estg.enums.OrderStatus;
import com.estg.messaging.OTPublisher;
import com.estg.models.OrdemTrabalho;
import com.estg.service.OrdemTrabalhoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repositorio")
public class OrdemTrabalhoController {

    private static final Logger logger = LoggerFactory.getLogger(OrdemTrabalhoController.class);

    @Autowired
    private OrdemTrabalhoService OtService;

    @Autowired
    private OTPublisher publisher;

    @GetMapping("/{id}")
    public ResponseEntity<OrdemTrabalhoDTO> getOT(@PathVariable Integer id) {
        logger.info("Finding ordem de trabalho com o ID: {}", id);
        OrdemTrabalhoDTO ordem = OtService.getOrdemTrabalhoById(id);
        return ResponseEntity.ok(ordem);
    }

    @GetMapping
    public ResponseEntity<List<OrdemTrabalhoDTO>> listOTs() {
        logger.info("Listing de todas as ordens de trabalho");
        List<OrdemTrabalhoDTO> ordens = OtService.getAllOTs();
        return ResponseEntity.ok(ordens);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrdemTrabalhoDTO>> listarPorStatus(@PathVariable String status) {
        logger.info("List de ordens de trabalho por status: {}", status);
        List<OrdemTrabalhoDTO> ordens = OtService.getOTsByStatus(OrderStatus.valueOf(status));
        return ResponseEntity.ok(ordens);
    }

    @GetMapping("/status/funcionario/{funcionarioId}")
    public ResponseEntity<List<OrdemTrabalhoDTO>> listarPorFuncionario(@PathVariable Integer funcionarioId) {
        logger.info("List de ordens de trabalho por id de funcionario: {}", funcionarioId);
        List<OrdemTrabalhoDTO> ordens = OtService.getOTsByFuncionario(funcionarioId);
        return ResponseEntity.ok(ordens);
    }

    @PostMapping
    public ResponseEntity<OrdemTrabalhoDTO> createOT(@RequestBody OrdemTrabalhoDTO ordemEntregaDTO) {
        logger.info("Received new OT id: {}", ordemEntregaDTO.getOrderId());
        OrdemTrabalhoDTO novaOrdem = OtService.addOrdemTrabalho(ordemEntregaDTO);

        publisher.sendOT(novaOrdem);
        logger.info("OT created with sucess: {}", novaOrdem.getOrderId());
        return ResponseEntity.ok(novaOrdem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdemTrabalhoDTO> atualizarOrdem(@PathVariable Integer id, @RequestBody OrdemTrabalhoDTO ordemEntregaDTO) {
        logger.info("Update na ordem de trabalho com o ID: {}", id);
        OrdemTrabalhoDTO ordemAtualizada = OtService.updateOrdemTrabalho(id, ordemEntregaDTO);
        return ResponseEntity.ok(ordemAtualizada);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrdemTrabalhoDTO> atualizarStatus(@PathVariable Integer id, @RequestBody OrdemTrabalhoDTO ordemEntregaDTO) {
        logger.info("Updating status da ordem de trabalho com o ID: {}", id);
        OrdemTrabalhoDTO ordemAtualizada = OtService.updateOTStatus(id, ordemEntregaDTO.getStatus());
        return ResponseEntity.ok(ordemAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarOrdem(@PathVariable Integer id) {
        logger.info("Removed Ordem de trabalho com o ID: {}", id);
        OtService.deleteOrdemTrabalho(id);
        return ResponseEntity.noContent().build();
    }

    // get information from other services

}
