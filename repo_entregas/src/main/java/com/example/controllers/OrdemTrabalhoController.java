package com.example.controllers;
import com.example.dtos.OrdemTrabalhoDTO;
import com.example.dtos.OrderDTO;
import com.example.dtos.RequisicaoAceitacaoDTO;
import com.example.enums.OrderStatus;
import com.example.exceptions.MissingDataException;
import com.example.exceptions.OrdemTrabalhoNotFound;
import com.example.exceptions.UtilizadorServiceUnexpectedException;
import com.example.exceptions.UtilizadoresUnexistingFuncionarioException;
import com.example.messaging.OTPublisher;
import com.example.models.OrdemTrabalho;
import com.example.service.OrdemTrabalhoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    ResponseEntity<?> add(@RequestBody OrderDTO orderDTO) {
        OrdemTrabalho newOrdem;
        try {
            // Create a new OrdemTrabalho entity and map fields from the DTO
            OrdemTrabalho ordem = new OrdemTrabalho();

            ordem.setMenuId(orderDTO.getMenuId());
            ordem.setQuantidade(orderDTO.getQuantity());
            ordem.setNomeCliente(orderDTO.getClientName());
            ordem.setContacto(orderDTO.getContact());
            ordem.setEnderecoEntrega(orderDTO.getDeliveryAddress());

            // Set default values for fields not provided by the client
            ordem.setStatus(OrderStatus.PENDING); // Default status
            ordem.setDataCriacao(new Date()); // Set creation date
            ordem.setFuncionarioId(null); // To be assigned later
            ordem.setDataEntrega(null); // To be updated later

            // Save the OrdemTrabalho using your service
            newOrdem = OtService.add(ordem);

            // Optionally publish an event after saving
            publisher.publish(newOrdem);

        } catch (Exception e) {
            // Handle any exceptions and return a BAD_REQUEST response
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

        // Map the saved entity back to a response DTO and return a CREATED response
        return new ResponseEntity<>(
                modelMapper.map(newOrdem, OrdemTrabalhoDTO.class), // Response DTO
                HttpStatus.CREATED
        );
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

    //testing only
    @GetMapping("/func/{funcionarioId}")
    ResponseEntity<?> getFuncionario (@PathVariable Integer funcionarioId) {
        try {
            return ResponseEntity.ok(OtService.getFuncionario(funcionarioId));
        } catch (UtilizadoresUnexistingFuncionarioException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UtilizadorServiceUnexpectedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (MissingDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}



