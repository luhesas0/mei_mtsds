package com.example.exceptions;

public class ErroInesperado extends Exception{
    @Override
    public String getMessage(){
        return "Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.";
    }
}
