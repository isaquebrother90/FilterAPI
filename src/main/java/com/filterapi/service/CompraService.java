package com.filterapi.service;

import com.filterapi.client.ProdutoClient;
import com.filterapi.client.ClienteCompraClient;
import com.filterapi.exception.*;
import com.filterapi.model.ClienteCompra;
import com.filterapi.model.Compra;
import com.filterapi.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompraService {

    @Autowired
    private ProdutoClient produtoClient;

    @Autowired
    private ClienteCompraClient clienteCompraClient;

    public List<Map<String, Object>> getCompras() {
        List<Produto> produtos = produtoClient.getProdutos();
        List<ClienteCompra> clientes = clienteCompraClient.getClientesCompras();

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

    public Map<String, Object> getMaiorCompraAno(int ano) {
        List<Produto> produtos = produtoClient.getProdutos();
        List<ClienteCompra> clientes = clienteCompraClient.getClientesCompras();

        double maiorValor = 0;
        Map<String, Object> maiorCompra = new HashMap<>();

        for (ClienteCompra cliente : clientes) {
            for (var compra : cliente.getCompras()) {
                Produto produto = produtos.stream()
                        .filter(p -> p.getCodigo() == Integer.parseInt(compra.getCodigo()) && p.getAnoCompra() == ano)
                        .findFirst()
                        .orElse(null);

                if (produto != null) {
                    double total = produto.getPreco() * compra.getQuantidade();
                    if (total > maiorValor) {
                        maiorValor = total;
                        maiorCompra.put("nome", cliente.getNome());
                        maiorCompra.put("cpf", cliente.getCpf());
                        maiorCompra.put("produto", produto);
                        maiorCompra.put("quantidade", compra.getQuantidade());
                        maiorCompra.put("valor_total", total);
                    }
                }
            }
        }

        return maiorCompra;
    }

    public List<Map<String, Object>> getClientesFieis() {
        List<Produto> produtos = produtoClient.getProdutos();
        List<ClienteCompra> clientes = clienteCompraClient.getClientesCompras();

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

    public String recomendacaoVinho(String cpf) {
        List<ClienteCompra> clientes = clienteCompraClient.getClientesCompras();

        Optional<ClienteCompra> clienteOpt = clientes.stream()
                .filter(cliente -> cliente.getCpf().equals(cpf))
                .findFirst();

        if (clienteOpt.isPresent()) {

            List<Compra> compras = clienteOpt.get().getCompras();

            if (compras.isEmpty()) {
                throw new ClienteSemComprasException(cpf);
            }

            Map<String, Long> codigoProdutoCompra = compras.stream()
                    .map(Compra::getCodigo)
                    .collect(Collectors.groupingBy(codigo -> codigo, Collectors.counting()));

            String codigoMaisComprado = codigoProdutoCompra.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);

            if (codigoMaisComprado != null) {
                List<Produto> produtos = produtoClient.getProdutos();
                Optional<Produto> produtoOpt = produtos.stream()
                        .filter(produto -> String.valueOf(produto.getCodigo()).equals(codigoMaisComprado)) // Filtra o produto pelo código
                        .findFirst();

                if (produtoOpt.isPresent()) {
                    return produtoOpt.get().getTipoVinho();
                } else {
                    throw new ProdutoNaoEncontradoException(codigoMaisComprado);
                }
            } else {
                throw new ProdutoSemCompraException();
            }
        } else {
            throw new ClienteNaoEncontradoException(cpf);
        }
    }
}
