package com.filterapi.exception;

public class ProdutoNaoEncontradoException extends RuntimeException {
    public ProdutoNaoEncontradoException(String codigoProduto) {
        super("Produto com código " + codigoProduto + " não encontrado.");
    }
}

