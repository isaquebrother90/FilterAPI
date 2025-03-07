package com.filterapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Produto {
  private int codigo;

  @JsonProperty("tipo_vinho")
  private String tipoVinho;

  private double preco;
  private String safra;

  @JsonProperty("ano_compra")
  private int anoCompra;

  public Produto() {}

  public Produto(int codigo, String tipoVinho, double preco, String safra, int anoCompra) {
    this.codigo = codigo;
    this.tipoVinho = tipoVinho;
    this.preco = preco;
    this.safra = safra;
    this.anoCompra = anoCompra;
  }

  public int getCodigo() {
    return codigo;
  }

  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  public String getTipoVinho() {
    return tipoVinho;
  }

  public void setTipoVinho(String tipoVinho) {
    this.tipoVinho = tipoVinho;
  }

  public double getPreco() {
    return preco;
  }

  public void setPreco(double preco) {
    this.preco = preco;
  }

  public String getSafra() {
    return safra;
  }

  public void setSafra(String safra) {
    this.safra = safra;
  }

  public int getAnoCompra() {
    return anoCompra;
  }

  public void setAnoCompra(int anoCompra) {
    this.anoCompra = anoCompra;
  }
}
