package com.filterapi.strategy.impl;

import com.filterapi.client.ClienteCompraApiClient;
import com.filterapi.client.ProdutoApiClient;
import com.filterapi.model.ClienteCompra;
import com.filterapi.model.Produto;
import com.filterapi.strategy.CompraStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ListarComprasStrategy implements CompraStrategy {

    @Autowired
    private ProdutoApiClient produtoApiClient;

    @Autowired
    private ClienteCompraApiClient clienteCompraApiClient;

    @Override
    public List<Map<String, Object>> calcular() {
        List<Produto> produtos = produtoApiClient.getProdutos();
        List<ClienteCompra> clientes = clienteCompraApiClient.getClientesCompras();
        List<Map<String, Object>> comprasDetalhadas = new ArrayList<>();

        for (ClienteCompra cliente : clientes) {
            for (var compra : cliente.getCompras()) {
                Produto produto = produtos.stream()
                        .filter(p -> p.getCodigo() == Integer.parseInt(compra.getCodigo()))
                        .findFirst()
                        .orElse(null);

                if (produto != null) {
                    double total = produto.getPreco() * compra.getQuantidade();
                    Map<String, Object> compraDetalhada = new HashMap<>();
                    compraDetalhada.put("nome", cliente.getNome());
                    compraDetalhada.put("cpf", cliente.getCpf());
                    compraDetalhada.put("produto", produto);
                    compraDetalhada.put("quantidade", compra.getQuantidade());
                    compraDetalhada.put("valor_total", total);
                    comprasDetalhadas.add(compraDetalhada);
                }
            }
        }

        return comprasDetalhadas.stream()
                .sorted(Comparator.comparingDouble(compra -> (double) compra.get("valor_total")))
                .collect(Collectors.toList());
    }
}
