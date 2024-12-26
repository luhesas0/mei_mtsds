package com.estg.service;

import com.estg.data.OrdemTrabalhoRepository;
import com.estg.dtos.OrdemTrabalhoDTO;
import com.estg.enums.OrderStatus;
import com.estg.exceptions.OrdemTrabalhoNotFound;
import com.estg.models.OrdemTrabalho;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdemTrabalhoService {

    private static final Logger logger = LoggerFactory.getLogger(OrdemTrabalhoService.class);

    @Autowired
    private OrdemTrabalhoRepository repository;

    private final ModelMapper modelMapper;

    public OrdemTrabalhoService() {
        this.modelMapper = new ModelMapper();
    }

    public List<OrdemTrabalhoDTO> getAllOTs() {
        logger.info("Listing all OTs");
        return repository.findAll().stream()
                .map(ot -> modelMapper.map(ot, OrdemTrabalhoDTO.class))
                .collect(Collectors.toList());
    }

    public OrdemTrabalhoDTO getOrdemTrabalhoById(Integer id) {
        logger.info("Finding order with ID: {}", id);
        return repository.findById(id)
                .map(ot -> modelMapper.map(ot, OrdemTrabalhoDTO.class))
                .orElseThrow(() -> new OrdemTrabalhoNotFound(id));
    }

    public OrdemTrabalhoDTO addOrdemTrabalho(OrdemTrabalhoDTO ordemTrabalhoDTO) {
        logger.info("Adding new OT: {}", ordemTrabalhoDTO.getOrder_id());
        ordemTrabalhoDTO.setStatus(OrderStatus.PENDING);
        OrdemTrabalho ordemTrabalho = modelMapper.map(ordemTrabalhoDTO, OrdemTrabalho.class);
        OrdemTrabalho savedOrdemTrabalho = repository.save(ordemTrabalho);
        return modelMapper.map(savedOrdemTrabalho, OrdemTrabalhoDTO.class);
    }

    public OrdemTrabalhoDTO updateOrdemTrabalho(Integer id, OrdemTrabalhoDTO ordemTrabalhoDTO) {
        logger.info("Updating order with ID: {}", id);
        OrdemTrabalho ordemTrabalho = modelMapper.map(ordemTrabalhoDTO, OrdemTrabalho.class);
        ordemTrabalho.setOrder_id(id);
        OrdemTrabalho savedOrdemTrabalho = repository.save(ordemTrabalho);
        return modelMapper.map(savedOrdemTrabalho, OrdemTrabalhoDTO.class);
    }

    public OrdemTrabalhoDTO updateOTStatus(Integer id, OrderStatus status) {
        logger.info("Updating order status with ID: {}", id);
        OrdemTrabalho ordemTrabalho = repository.findById(id)
                .orElseThrow(() -> new OrdemTrabalhoNotFound(id));
        ordemTrabalho.setStatus(status);
        OrdemTrabalho savedOrdemTrabalho = repository.save(ordemTrabalho);
        return modelMapper.map(savedOrdemTrabalho, OrdemTrabalhoDTO.class);
    }

    public void deleteOrdemTrabalho(Integer id) {
        logger.info("Removendo ordem de entrega com ID: {}", id);
        repository.deleteById(id);
    }
}