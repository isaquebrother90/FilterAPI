package com.filterapi.exception;

public class ClienteSemComprasException extends RuntimeException {
    public ClienteSemComprasException(String cpf) {
        super("Cliente com CPF " + cpf + " não fez compras.");
    }
}
