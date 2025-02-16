package com.filterapi.model;

public class Compra {
    private String codigo;
    private int quantidade;

    public Compra() {
    }

    public Compra(String codigo, int quantidade) {
        this.codigo = codigo;
        this.quantidade = quantidade;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
