package com.example.service;

import com.example.data.OrdemTrabalhoRepository;
import com.example.dtos.FuncionarioDTO;
import com.example.dtos.OrderDTO;
import com.example.dtos.RequisicaoAceitacaoDTO;
import com.example.enums.OrderStatus;
import com.example.exceptions.*;
import com.example.models.OrdemTrabalho;
import jdk.swing.interop.SwingInterOpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrdemTrabalhoService {

    private static final Logger logger = LoggerFactory.getLogger(OrdemTrabalhoService.class);

    @Value("${endpoints.gestao_utilizadores.url}")
    private String gestaoUtilizadoresUrl;

    @Value("${endpoints.criacao_menu.url}")
    private String gestaoMenusUrl;

    @Autowired
    private OrdemTrabalhoRepository repository;

    @Autowired
    private RabbitTemplate rabbit;
    @Autowired
    private OrdemTrabalhoRepository ordemTrabalhoRepository;

    public List<OrdemTrabalho> getAll() {
        logger.info("(LS) Listing all OTs");
        return repository.findAll();
    }

    public OrdemTrabalho get(Integer id) throws OrdemTrabalhoNotFound {
        logger.info("(LS) Finding order with ID: {}", id);
        return repository
                .findById(id)
                .orElseThrow(() -> new OrdemTrabalhoNotFound(id));
    }

    @Transactional
    public OrdemTrabalho update(OrdemTrabalho ordemTrabalho) throws OrdemTrabalhoNotFound {
        if(repository.existsById(ordemTrabalho.getOrderId())) {
            logger.info("(LS) Updating order with ID: {}", ordemTrabalho.getOrderId());
            return repository.save(ordemTrabalho);
        } else {
            logger.info("(LS) Order with ID: {} not found", ordemTrabalho.getOrderId());
            throw new OrdemTrabalhoNotFound(ordemTrabalho.getOrderId());
        }
    }

    @Transactional
    public OrdemTrabalho add(OrdemTrabalho ordemTrabalho) {
        if (ordemTrabalho.getOrderId() != null && repository.existsById(ordemTrabalho.getOrderId())) {
            logger.info("(LS) Order with ID: {} already exists", ordemTrabalho.getOrderId());
            return null;
        } else {
            logger.info("(LS) Adding new order with ID: {}", ordemTrabalho.getOrderId());
            return repository.save(ordemTrabalho);
        }
    }

    public List<OrdemTrabalho> getStatus(OrderStatus status) throws OrdemTrabalhoNotFound {
        if(repository.findByStatus(status).isEmpty()) {
            logger.info("(LS) No orders found with status: {}", status);
            return null;
        } else {
            logger.info("(LS) Listing all orders with status: {}", status);
            return repository.findByStatus(status);
        }
    }

    public List<OrdemTrabalho> getPorFuncionario(Integer funcionario_id) {
        if(repository.findByFuncionarioId(funcionario_id).isEmpty()) {
            logger.info("No orders found with funcionario ID: {}", funcionario_id);
            return null;
        } else {
            logger.info("Listing all orders with funcionario ID: {}", funcionario_id);
            return repository.findByFuncionarioId(funcionario_id);
        }
    }

    private FuncionarioDTO getFuncionario(Integer funcionario_id)
            throws UtilizadorServiceUnexpectedException, UtilizadoresUnexistingFuncionarioException, MissingDataException {

        FuncionarioDTO funcionarioDTO;
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<FuncionarioDTO> response = restTemplate.getForEntity(
                    gestaoUtilizadoresUrl + "/{funcionarioId}",
                    FuncionarioDTO.class,
                    funcionario_id
            );
            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            if(e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new UtilizadorServiceUnexpectedException();
            } else if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new UtilizadoresUnexistingFuncionarioException();
            } else {
                throw new MissingDataException();
            }
        }
    }

    @Transactional
    public void assignOT(Integer OrdemTrabalhoId, Integer funcionarioId)
            throws OrdemTrabalhoNotFound, UtilizadorServiceUnexpectedException, UtilizadoresUnexistingFuncionarioException, MissingDataException {

        // TODO: Adicionar lógica de verificação de capacidade de veículo


        OrdemTrabalho ordemTrabalho = get(OrdemTrabalhoId);
        FuncionarioDTO funcionarioDTO = getFuncionario(funcionarioId);

        if (ordemTrabalho == null || funcionarioDTO == null) {
            logger.info("Order with ID: {} or funcionario with ID: {} not found", OrdemTrabalhoId, funcionarioId);
            throw new OrdemTrabalhoNotFound(OrdemTrabalhoId);
        } else {
            logger.info("Order with ID: {} and funcionario with ID: {} found", OrdemTrabalhoId, funcionarioId);
        }

        ordemTrabalho.setFuncionarioId(funcionarioId);
        ordemTrabalho.setStatus(OrderStatus.ACCEPTED);

        logger.info("Assigning order with ID: {} to funcionario with ID: {}", OrdemTrabalhoId, funcionarioId);
        repository.save(ordemTrabalho);
    }

    public OrdemTrabalho updateOTStatus(Integer id, OrderStatus status) throws OrdemTrabalhoNotFound {
        OrdemTrabalho ordemTrabalho = get(id);
        ordemTrabalho.setStatus(status);
        logger.info("Updating order with ID: {} to status: {}", id, status);
        return repository.save(ordemTrabalho);
    }

    public void delete(Integer id) throws OrdemTrabalhoNotFound {
        if(repository.existsById(id)) {
            logger.info("Deleting order with ID: {}", id);
            repository.deleteById(id);
        } else {
            logger.info("Order with ID: {} not found", id);
            throw new OrdemTrabalhoNotFound(id);
        }
    }

    public String acceptOrdem(RequisicaoAceitacaoDTO requisicao, String otQueue) throws UtilizadoresUnexistingFuncionarioException, UtilizadorServiceUnexpectedException, MissingDataException {
        Object message = rabbit.receiveAndConvert(otQueue);
        //FuncionarioDTO funcionario = getFuncionario(requisicao.getFuncionarioId());

        if (message == null) {
            return "No message found";
        }

        try {
            OrdemTrabalho ordemTrabalho = ordemTrabalhoRepository.findById(requisicao.getOrdemTrabalhoId())
                    .orElseThrow(() -> new InvalidOrdemTrabalho(requisicao.getOrdemTrabalhoId()));

            if (ordemTrabalho.getStatus() == OrderStatus.ACCEPTED) {
                return "Ordem de trabalho já aceita.";
            }

            if(requisicao.isAceite()) {
                ordemTrabalho.setStatus(OrderStatus.ACCEPTED);
                ordemTrabalho.setFuncionarioId(requisicao.getFuncionarioId());
                System.out.println("Ordem de trabalho aceite (verificacao de capacidade de veiculo)");
                ordemTrabalhoRepository.save(ordemTrabalho);
            } else {
                ordemTrabalho.setStatus(OrderStatus.CANCELED);
            }

            ordemTrabalho.setStatus(OrderStatus.ACCEPTED);
            ordemTrabalho.setFuncionarioId(requisicao.getFuncionarioId());
            ordemTrabalhoRepository.save(ordemTrabalho);

            return "Ordem de trabalho aceite";
        } catch (Exception e) {
            return "Erro ao consumir message no rabbit: " + e.getMessage();
        }
    }
}