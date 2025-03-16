package com.filterapi.exception;

/**
 * Exceção lançada quando um cliente não é encontrado na base de dados. Esta exceção específica
 * inclui o CPF do cliente não encontrado na mensagem, facilitando a identificação do cliente que
 * causou o erro.
 *
 * @since 1.0
 */
public class ClienteNaoEncontradoException extends RuntimeException {
  public ClienteNaoEncontradoException(String cpf) {
    super("Cliente com CPF " + cpf + " não encontrado.");
  }
}
