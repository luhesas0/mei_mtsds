package org.example.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exceção para indicar que um item não foi encontrado.
 */
@Getter
@AllArgsConstructor
public class ItemNaoExiste extends RuntimeException {
    private final String item;

    @Override
    public String getMessage(){
        return String.format("O item '%s' não foi encontrado no sistema.", item);
    }
}
