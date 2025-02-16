package com.filterapi.client.Request;

public class ClienteRequest {
    private String cpf;

    public ClienteRequest() {
    }

    public ClienteRequest(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}