package com.filterapi.model;

import java.util.List;

/**
 * Classe que representa um cliente com suas compras. Esta classe contém informações sobre o nome,
 * CPF e a lista de compras associadas a um cliente.
 *
 * @since 1.0
 */
public class ClienteCompra {
  private String nome;
  private String cpf;
  private List<Compra> compras;

  public ClienteCompra() {}

  public ClienteCompra(String nome, String cpf, List<Compra> compras) {
    this.nome = nome;
    this.cpf = cpf;
    this.compras = compras;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public List<Compra> getCompras() {
    return compras;
  }

  public void setCompras(List<Compra> compras) {
    this.compras = compras;
  }
}
