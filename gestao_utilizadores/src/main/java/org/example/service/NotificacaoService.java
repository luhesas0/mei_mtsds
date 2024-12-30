package org.example.service;

import org.example.dto.NotificacaoDto;
import org.example.models.Notificacoes;
import org.example.models.Utilizador;
import org.example.repository.NotificacoesRepository;
import org.example.repository.UtilizadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacoesRepository notificacoesRepository;

    @Autowired
    private UtilizadorRepository utilizadorRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Envia uma notificação para um utilizador.
     * @param userId ID do utilizador.
     * @param mensagem Mensagem da notificação.
     */
    public void enviarNotificação(Long userId, String mensagem){
        Utilizador utilizador = utilizadorRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Utilizador não encontrado."));

        Notificacoes notificacao = new Notificacoes();
        notificacao.setUtilizador(utilizador);
        notificacao.setMensagem(mensagem);
        notificacao.setDataEnvio(LocalDateTime.now());
        notificacoesRepository.save(notificacao);
    }

    /**
     * Lista notificações enviadas para um utilizador.
     * @param userId ID do utilizador.
     * @return Lista de DTOs de notificações.
     */
    public List<NotificacaoDto> listarNotificacoes(Long userId){
        return notificacoesRepository.findAll().stream()
                .filter(n -> n.getUtilizador().getId().equals(userId))
                .map(notificacao -> modelMapper.map(notificacao, NotificacaoDto.class))
                .collect(Collectors.toList());
    }
}
