package com.filterapi.service.Impl;

import com.filterapi.client.ProdutoApiClient;
import com.filterapi.client.ClienteCompraApiClient;
import com.filterapi.exception.*;
import com.filterapi.model.ClienteCompra;
import com.filterapi.model.Compra;
import com.filterapi.model.Produto;
import com.filterapi.service.CompraService;
import com.filterapi.strategy.CompraStrategyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompraServiceImpl implements CompraService {

    @Autowired
    private ProdutoApiClient produtoApiClient;

    @Autowired
    private ClienteCompraApiClient clienteCompraApiClient;

    @Autowired
    private CompraStrategyContext strategyContext;

    @Override
    public List<Map<String, Object>> getCompras() {
        return strategyContext.executarEstrategia("ListarComprasStrategy");
    }

    @Override
    public List<Map<String, Object>> getClientesFieis() {
        return strategyContext.executarEstrategia("ClientesFieisStrategy");
    }

    @Override
    public Map<String, Object> getMaiorCompraAno(int ano) {
        List<Produto> produtos = produtoApiClient.getProdutos();
        List<ClienteCompra> clientes = clienteCompraApiClient.getClientesCompras();

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

    @Override
    public String recomendacaoVinho(String cpf) {
        List<ClienteCompra> clientes = clienteCompraApiClient.getClientesCompras();

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
                List<Produto> produtos = produtoApiClient.getProdutos();
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
