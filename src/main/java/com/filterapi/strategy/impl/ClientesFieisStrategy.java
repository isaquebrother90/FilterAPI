package com.filterapi.strategy.impl;

import com.filterapi.client.ClienteCompraApiClient;
import com.filterapi.client.ProdutoApiClient;
import com.filterapi.model.ClienteCompra;
import com.filterapi.model.Produto;
import com.filterapi.strategy.CompraStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ClientesFieisStrategy implements CompraStrategy {

    @Autowired
    private ProdutoApiClient produtoApiClient;

    @Autowired
    private ClienteCompraApiClient clienteCompraApiClient;

    @Override
    public List<Map<String, Object>> calcular() {
        List<Produto> produtos = produtoApiClient.getProdutos();
        List<ClienteCompra> clientes = clienteCompraApiClient.getClientesCompras();

        Map<String, Double> clienteFidelidade = new HashMap<>();

        for (ClienteCompra cliente : clientes) {
            double totalValor = 0;
            for (var compra : cliente.getCompras()) {
                Produto produto = produtos.stream()
                        .filter(p -> p.getCodigo() == Integer.parseInt(compra.getCodigo()))
                        .findFirst()
                        .orElse(null);

                if (produto != null) {
                    totalValor += produto.getPreco() * compra.getQuantidade();
                }
            }
            clienteFidelidade.put(cliente.getCpf(), totalValor);
        }

        return clienteFidelidade.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .limit(3)
                .map(entry -> {
                    ClienteCompra cliente = clientes.stream()
                            .filter(c -> c.getCpf().equals(entry.getKey()))
                            .findFirst()
                            .orElse(null);
                    Map<String, Object> clienteFiel = new HashMap<>();
                    if (cliente != null) {
                        clienteFiel.put("nome", cliente.getNome());
                        clienteFiel.put("cpf", cliente.getCpf());
                        clienteFiel.put("total_gasto", entry.getValue());
                    }
                    return clienteFiel;
                })
                .collect(Collectors.toList());
    }
}
