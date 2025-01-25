package com.example.service;

import com.example.dto.CriarNotificacaoDTO;
import com.example.dto.NotificacaoResumoDTO;
import com.example.exceptions.RecursoNaoEncontradoException;
import com.example.messages.NotificacaoPublisher;
import com.example.models.Notificacao;
import com.example.repository.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerir notificações.
 */

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final NotificacaoPublisher notificacaoPublisher;
    private final ModelMapper modelMapper;

    /**
     *  Registar uma nova notificação e publicá-la no RabbitMQ.
     *
     * @param dto Dados da notificação a ser registada.
     * @return NotificacaoResumoDTO com os detalhes da notificação criada.
     */
    public NotificacaoResumoDTO registarNotificacao(CriarNotificacaoDTO dto){
        // Salva na base de dados
        Notificacao notificacao = modelMapper.map(dto, Notificacao.class);
        Notificacao salva = notificacaoRepository.save(notificacao);

        // Publicar a mensagem no RabbitMQ
        notificacaoPublisher.enviarNotificacao(dto);

        // Retornar o DTO da notificação criada
        return modelMapper.map(salva, NotificacaoResumoDTO.class);
    }

    /**
     *  Listar notificações de um utilizador.
     *
     * @param utilizadorId ID do utilizador.
     * @return Lista de NotificacaoResumoDTO.
     */
    public List<NotificacaoResumoDTO> listarNotificacoesPorUtilizador(Long utilizadorId){
        List<Notificacao> notificacoes = notificacaoRepository.findByUtilizadorId(utilizadorId);

        if(notificacoes.isEmpty()){
            throw new RecursoNaoEncontradoException("listarNotificacoesPorUtilizador", "Notificações para o utilizador" + utilizadorId);
        }

        return notificacoes.stream()
                .map(notificacao -> modelMapper.map(notificacao, NotificacaoResumoDTO.class))
                .collect(Collectors.toList());
    }
}
