package com.filterapi.exception;

/**
 * Exceção lançada quando um cliente com um determinado CPF não possui compras registradas. Esta
 * exceção é utilizada para sinalizar a ausência de compras para um cliente específico, facilitando
 * o tratamento desse cenário no fluxo de negócio.
 *
 * @since 1.0
 */
public class ClienteSemComprasException extends RuntimeException {
  public ClienteSemComprasException(String cpf) {
    super("Cliente com CPF " + cpf + " não fez compras.");
  }
}
