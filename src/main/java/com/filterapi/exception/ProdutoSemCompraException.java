package com.filterapi.exception;

/**
 * Exceção lançada quando nenhum produto é encontrado para um determinado cliente.
 *
 * @since 1.0
 */
public class ProdutoSemCompraException extends RuntimeException {
  public ProdutoSemCompraException() {
    super("Nenhum produto encontrado para o cliente.");
  }
}
