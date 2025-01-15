package org.example.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exceção para indicar que um item já existe.
 */
@Getter
@AllArgsConstructor
public class ItemJaExiste extends RuntimeException {
    private final String item;

    @Override
    public String getMessage(){
        return String.format("O item '%' já existe no sistema.", item);
    }
}
