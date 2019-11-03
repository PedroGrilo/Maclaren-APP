package com.gabrielmiguelpedro.maclarenapp.Exceptions;

public class EmptyFieldException extends Exception {
    public EmptyFieldException() {
        super("Campo Vazio");
    }
}
