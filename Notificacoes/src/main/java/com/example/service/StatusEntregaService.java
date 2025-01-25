package com.example.service;

import com.example.dto.AtualizarStatusEntregaDTO;
import com.example.dto.StatusEntregaResumoDTO;
import com.example.enums.StatusEntregaEnum;
import com.example.exceptions.RecursoNaoEncontradoException;
import com.example.messages.NotificacaoPublisher;
import com.example.models.StatusEntrega;
import com.example.repository.StatusEntregaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerir o status das entregas.
 */
@Service
@RequiredArgsConstructor
public class StatusEntregaService {

    private final StatusEntregaRepository statusEntregaRepository;
    private final NotificacaoPublisher notificacaoPublisher;
    private final ModelMapper modelMapper;

    /**
     * Atualizar o status de uma entrega Atualizar o status de uma entrega e publicá-lo no RabbitMQ..
     *
     * @param dto Dados para atualização do status.
     * @return StatusEntregaResumoDTO com os detalhes atualizados.
     */
    public StatusEntregaResumoDTO atualizarStatusEntrega(AtualizarStatusEntregaDTO dto){
        // Atualizar na base de dados
        StatusEntrega statusEntrega = modelMapper.map(dto, StatusEntrega.class);
        statusEntrega.setStatus(StatusEntregaEnum.valueOf(dto.getStatus()));
        statusEntrega.setAtualizadoEm(LocalDateTime.now());
        StatusEntrega salvo = statusEntregaRepository.save(statusEntrega);

        // Publicar a mensagem no RabbitMQ
        notificacaoPublisher.enviarStatusEntrega(dto);

        // Retornar o DTO com os detalhes do status atualizado
        return modelMapper.map(salvo, StatusEntregaResumoDTO.class);
    }

    /**
     *  Consultar status por ID da entrega.
     *
     * @param entregaId ID da entrega.
     * @return Lista de StatusEntregaResumoDTO.
     */
    public List<StatusEntregaResumoDTO> consultarStatusPorEntrega(Long entregaId){
        List<StatusEntrega> statusList = statusEntregaRepository.findByEntregaId(entregaId);

        if(statusList.isEmpty()){
            throw new RecursoNaoEncontradoException("consultarStatusPorEntrega", "Status para a entrega" + entregaId);
        }

        return statusList.stream()
                .map(status -> modelMapper.map(status, StatusEntregaResumoDTO.class))
                .collect(Collectors.toList());
    }
}
