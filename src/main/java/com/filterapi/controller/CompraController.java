package com.filterapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Esta classe Controller expõe endpoints para gerenciamento de compras.=
 *
 * @author Isaque Moura
 * @version 1.0
 * @since 1.0
 */
public interface CompraController {

  @Operation(
      summary = "Listar todas as compras",
      description = "Retorna uma lista de todas as compras registradas",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Compras encontradas",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Map.class),
                    examples =
                        @ExampleObject(
                            value =
                                "[{\"id\":1, \"produto\": \"Produto A\", \"valor\": 150.0}, {\"id\":2, \"produto\": \"Produto B\", \"valor\": 200.0}]"))),
        @ApiResponse(responseCode = "204", description = "Nenhuma compra encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      })
  @GetMapping("/compras")
  List<Map<String, Object>> getCompras();

  @Operation(
      summary = "Obter a maior compra de um ano",
      description = "Retorna a maior compra realizada em um ano específico",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Maior compra encontrada",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Map.class),
                    examples =
                        @ExampleObject(
                            value = "{\"id\":1, \"produto\": \"Produto A\", \"valor\": 500.0}"))),
        @ApiResponse(
            responseCode = "404",
            description = "Nenhuma compra encontrada para o ano fornecido"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      })
  @GetMapping("/maior-compra/{ano}")
  Map<String, Object> getMaiorCompra(
      @Parameter(description = "Ano para buscar a maior compra") @PathVariable int ano);

  @Operation(
      summary = "Listar os clientes mais fiéis",
      description = "Retorna uma lista de clientes que mais realizaram compras",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Clientes fiéis encontrados",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Map.class),
                    examples =
                        @ExampleObject(
                            value =
                                "[{\"id\":1, \"cliente\": \"Cliente A\", \"compras\": 10}, {\"id\":2, \"cliente\": \"Cliente B\", \"compras\": 8}]"))),
        @ApiResponse(responseCode = "204", description = "Nenhum cliente fiel encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      })
  @GetMapping("/clientes-fieis")
  List<Map<String, Object>> getClientesFieis();

  @Operation(
      summary = "Obter recomendação de vinho",
      description = "Retorna uma recomendação de vinho com base no CPF do cliente",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Recomendação de vinho encontrada",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "\"Vinho Tinto - Cabernet Sauvignon\""))),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
      })
  @GetMapping("/recomendacao/{cpf}")
  String getRecomendacao(
      @Parameter(description = "CPF do cliente para obter a recomendação") @PathVariable
          String cpf);
}
