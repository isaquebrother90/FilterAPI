package com.filterapi.exception;

public class ProdutoSemCompraException extends RuntimeException {
    public ProdutoSemCompraException() {
        super("Nenhum produto encontrado para o cliente.");
    }
}
