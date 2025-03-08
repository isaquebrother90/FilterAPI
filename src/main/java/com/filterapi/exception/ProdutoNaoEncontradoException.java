package com.filterapi.exception;

/**
 * Exceção lançada quando um produto não é encontrado na base de dados. Esta exceção específica
 * inclui o código do produto não encontrado na mensagem, facilitando a identificação do produto que
 * causou o erro.
 *
 * @since 1.0
 */
public class ProdutoNaoEncontradoException extends RuntimeException {
  public ProdutoNaoEncontradoException(String codigoProduto) {
    super("Produto com código " + codigoProduto + " não encontrado.");
  }
}
