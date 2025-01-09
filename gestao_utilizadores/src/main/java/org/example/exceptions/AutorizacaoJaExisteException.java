package org.example.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Para o Contexto Gestão de Autorização de Tarefas
 * Exceção personalizada para autorizações duplicadas.
 */
@Getter
@AllArgsConstructor
public class AutorizacaoJaExistenteException extends RuntimeException {

    /**
     * Construtor.
     */
    public AutorizacaoJaExistenteException(String mensagem){
        super(mensagem);
    }
}
