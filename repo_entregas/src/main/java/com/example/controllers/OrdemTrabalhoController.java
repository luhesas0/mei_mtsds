package com.example.controllers;
import com.example.dtos.OrdemTrabalhoDTO;
import com.example.dtos.RequisicaoAceitacaoDTO;
import com.example.enums.OrderStatus;
import com.example.exceptions.MissingDataException;
import com.example.exceptions.OrdemTrabalhoNotFound;
import com.example.exceptions.UtilizadorServiceUnexpectedException;
import com.example.exceptions.UtilizadoresUnexistingFuncionarioException;
import com.example.messaging.OTPublisher;
import com.example.models.OrdemTrabalho;
import com.example.service.OrdemTrabalhoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/repositorio")
public class OrdemTrabalhoController {

    private static final Logger logger = LoggerFactory.getLogger(OrdemTrabalhoController.class);

    @Autowired
    private OrdemTrabalhoService OtService;

    private final ModelMapper modelMapper;

    @Autowired
    private OTPublisher publisher;

    public OrdemTrabalhoController() {
        this.modelMapper = new ModelMapper();
    }

    @GetMapping("/landing")
    public ResponseEntity<String> landing() {
        return ResponseEntity.status(HttpStatus.OK).body("Repo Entregas working!");
    }

    @GetMapping
    ResponseEntity<List<OrdemTrabalhoDTO>> getAll() {
        List<OrdemTrabalho> ordens = OtService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                ordens.stream()
                        .map(ordem -> modelMapper.map(ordem, OrdemTrabalhoDTO.class))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<?> get(@PathVariable Integer id) {
        OrdemTrabalho ordem;
        try {
            ordem = OtService.get(id);
        } catch (OrdemTrabalhoNotFound e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(modelMapper.map(ordem, OrdemTrabalhoDTO.class));
    }

    @PostMapping
    ResponseEntity<?> add(@RequestBody OrdemTrabalhoDTO ordemEntregaDTO) {
        OrdemTrabalho newOrdem;
        try {
            newOrdem = OtService.add(modelMapper.map(ordemEntregaDTO, OrdemTrabalho.class));
            publisher.publish(newOrdem);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        return new ResponseEntity<>(
                modelMapper.map(newOrdem, OrdemTrabalhoDTO.class),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable Integer id, @RequestBody OrdemTrabalhoDTO ordemEntregaDTO) {
        OrdemTrabalho updatedOrdem;

        if(!Objects.equals(ordemEntregaDTO.getOrderId(), id)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("(LS) (OrdemTrabalhoUpdate): Id inconsistency");
        }

        try {
            updatedOrdem = OtService.update(modelMapper.map(ordemEntregaDTO, OrdemTrabalho.class));
        } catch (OrdemTrabalhoNotFound e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        return new ResponseEntity<>(
                modelMapper.map(updatedOrdem, OrdemTrabalhoDTO.class),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            OtService.delete(id);
        } catch (OrdemTrabalhoNotFound e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/status/{status}")
    ResponseEntity<List<OrdemTrabalhoDTO>> getPorStatus(@PathVariable String status) {
        List<OrdemTrabalho> ordens = OtService.getStatus(OrderStatus.valueOf(status));
        return ResponseEntity.status(HttpStatus.OK).body(
                ordens.stream()
                        .map(ordem -> modelMapper.map(ordem, OrdemTrabalhoDTO.class))
                        .collect(Collectors.toList())
        );
    }

    @PostMapping("/{id}/accept")
    ResponseEntity<?> acceptOrdem(@PathVariable Integer id, @RequestBody RequisicaoAceitacaoDTO requisicao) throws UtilizadoresUnexistingFuncionarioException, UtilizadorServiceUnexpectedException, MissingDataException {

        if (!id.equals(requisicao.getOrdemTrabalhoId())) {
            return ResponseEntity.badRequest().body("Order ID in the URL does not match the DTO");
        }

        String response = OtService.acceptOrdem(requisicao, "OT_queue");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<List<OrdemTrabalhoDTO>> getPorFuncionario(@PathVariable Integer funcionarioId) {
        logger.info("List de ordens de trabalho por id de funcionario: {}", funcionarioId);

        List<OrdemTrabalho> ordens = OtService.getPorFuncionario(funcionarioId);

        return ResponseEntity.status(HttpStatus.OK).body(
                ordens.stream()
                        .map(ordem -> modelMapper.map(ordem, OrdemTrabalhoDTO.class))
                        .collect(Collectors.toList())
        );
    }

    @PutMapping("/{id}/assign/{funcionarioId}")
    ResponseEntity<?> assignFuncionario(@PathVariable Integer id, @PathVariable Integer funcionarioId) {
        OrdemTrabalho updatedOrdem;
        try {
           //updatedOrdem = OtService.assignOT(OTid, funcionarioId);
        } catch (OrdemTrabalhoNotFound e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        return null;
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Integer id, @RequestBody OrdemTrabalhoDTO ordemEntregaDTO) {
        logger.info("Updating status da ordem de trabalho com o ID: {}", id);
        OrdemTrabalho ordemAtualizada;

        ordemAtualizada = OtService.updateOTStatus(id, ordemEntregaDTO.getStatus());
        return ResponseEntity.ok(ordemAtualizada);
    }
}



