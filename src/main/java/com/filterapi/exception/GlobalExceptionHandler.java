package com.filterapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Classe global de tratamento de exceções. Esta classe intercepta e trata as exceções lançadas pela
 * API, retornando respostas HTTP adequadas com códigos de status e mensagens informativas.
 *
 * @since 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ClienteNaoEncontradoException.class)
  public ResponseEntity<String> handleClienteNaoEncontradoException(
      ClienteNaoEncontradoException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ProdutoNaoEncontradoException.class)
  public ResponseEntity<String> handleProdutoNaoEncontradoException(
      ProdutoNaoEncontradoException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ClienteSemComprasException.class)
  public ResponseEntity<String> handleClienteSemComprasException(ClienteSemComprasException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ProdutoSemCompraException.class)
  public ResponseEntity<String> handleProdutoSemCompraException(ProdutoSemCompraException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGenericException(Exception ex) {
    return new ResponseEntity<>(
        "Ocorreu um erro inesperado: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
