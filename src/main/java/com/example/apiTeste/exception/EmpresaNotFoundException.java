package com.example.apiTeste.exception;

public class EmpresaNotFoundException extends RuntimeException{
    public EmpresaNotFoundException(String message) {
        super(message);
    }
}
