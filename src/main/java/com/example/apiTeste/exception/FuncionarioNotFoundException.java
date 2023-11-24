package com.example.apiTeste.exception;

public class FuncionarioNotFoundException extends RuntimeException{
    public FuncionarioNotFoundException(String message) {
        super(message);
    }
}
