package com.example.service;

import com.example.dto.*;
import com.example.enums.Criterio;
import com.example.exceptions.RecursoNaoEncontradoException;
import com.example.exceptions.ValidacaoDadosException;
import com.example.models.Avaliacao;
import com.example.repository.AvaliacaoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerir avaliações.
 */
@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final ModelMapper modelMapper;

    /**
     *  Registar uma nova avaliação.
     *
     * @param dto Dados da avaliação a ser registada.
     * @return AvaliacaoResumoDTO com os detalhes da avaliação criada.
     */
    public AvaliacaoResumoDTO registarAvaliacao(CriarAvaliacaoDTO dto){
        validarNota(dto.getNota());

        //Mapear o DTO para a entidade Avaliacao
        Avaliacao avaliacao = modelMapper.map(dto, Avaliacao.class);
        avaliacao.setCriterio(Criterio.valueOf(dto.getCriterio()));

        //Salvar a entidade no repositório
        Avaliacao salva = avaliacaoRepository.save(avaliacao);

        // Mapear a entidade salva de volta para o DTO de saída
        return modelMapper.map(salva, AvaliacaoResumoDTO.class);
    }

    /**
     * Validar a nota fornecida.
     *
     * @param nota Nota de avaliação (deve estar entre 1 e 5 - Muito satisfeito).
     * @throws ValidacaoDadosException Se a nota for inválida.
     */
    private void validarNota(Integer nota){
        if(nota == null || nota<1 || nota >5){
            throw new ValidacaoDadosException("registarAvaliacao", "A nota deve estar entre 1 e 5.");
        }
    }

    /**
     * Listar todas as avaliações de um utilizador.
     *
     * @param utilizadorId ID do utilizador.
     * @return Lista de AvaliacaoResumoDTO.
     */
    public List<AvaliacaoResumoDTO> listarAvaliacoesPorUtilizador(Long utilizadorId){
        // Buscar avaliações no repositório
        List<Avaliacao> avaliacoes = avaliacaoRepository.findByUtilizadorId(utilizadorId);

        // Lançar exceção se não houver avaliações
        if(avaliacoes.isEmpty()){
            throw new RecursoNaoEncontradoException(
                    "listarAvaliacoesPorUtilizador", //origem da exceção
                    "Avaliacoes não encontradas para o utilizador com ID" + utilizadorId);
        }

        // Converter para DTO e retornar
        return avaliacoes.stream()
                .map(avaliacao -> modelMapper.map(avaliacao, AvaliacaoResumoDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Calcular a média de avaliações de um utilizador.
     *
     * @param utilizadorId ID do utilizador.
     * @return MediaAvaliacaoDTO com os detalhes da média calculada.
     */
    public MediaAvaliacaoDTO calcularMediaAvaliacoes(Long utilizadorId){
        List<Avaliacao> avaliacoes = avaliacaoRepository.findByUtilizadorId(utilizadorId);

        if(avaliacoes.isEmpty()){
            throw new RecursoNaoEncontradoException("calcularMediaAvalicoes", "Avaliações para o utilizador" + utilizadorId);
        }

        double media = avaliacoes.stream()
                .mapToInt(Avaliacao::getNota)
                .average()
                .orElse(0.0);

        return new MediaAvaliacaoDTO(utilizadorId, media, (long) avaliacoes.size());
    }
}
