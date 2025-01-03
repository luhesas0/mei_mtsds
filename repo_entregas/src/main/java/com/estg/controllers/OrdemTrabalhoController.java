package com.estg.controllers;
import com.estg.dtos.OrdemTrabalhoDTO;
import com.estg.enums.OrderStatus;
import com.estg.exceptions.OrdemTrabalhoNotFound;
import com.estg.messaging.OTPublisher;
import com.estg.models.OrdemTrabalho;
import com.estg.service.OrdemTrabalhoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/repositorio")
public class OrdemTrabalhoController {

    private static final Logger logger = LoggerFactory.getLogger(OrdemTrabalhoController.class);

    @Autowired
    private OrdemTrabalhoService OtService;

    private ModelMapper modelMapper;

    @Autowired
    private OTPublisher publisher;

    public OrdemTrabalhoController() {
        modelMapper = new ModelMapper();
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
            newOrdem = OtService.add
                    (modelMapper.map(ordemEntregaDTO, OrdemTrabalho.class));
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

        if(ordemEntregaDTO.getId() != id) {
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
    public ResponseEntity<?> delete(@PathVariable Integer id) {
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

//    @GetMapping("/status/funcionario/{funcionarioId}")
//    public ResponseEntity<List<OrdemTrabalhoDTO>> getPorFuncionario(@PathVariable Integer funcionarioId) {
//        logger.info("List de ordens de trabalho por id de funcionario: {}", funcionarioId);
//        List<OrdemTrabalhoDTO> ordens = OtService.getOTsByFuncionario(funcionarioId);
//        return ResponseEntity.ok(ordens);
//    }

//    @PutMapping("/{id}/status")
//    public ResponseEntity<?> atualizarStatus(@PathVariable Integer id, @RequestBody OrdemTrabalhoDTO ordemEntregaDTO) {
//        logger.info("Updating status da ordem de trabalho com o ID: {}", id);
//        OrdemTrabalhoDTO ordemAtualizada = OtService.updateOTStatus(id, ordemEntregaDTO.getStatus());
//        return ResponseEntity.ok(ordemAtualizada);
//    }

    // get information from other services

}
