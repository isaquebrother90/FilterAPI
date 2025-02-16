package com.filterapi.service;

import com.filterapi.client.ClienteCompraClient;
import com.filterapi.client.ProdutoClient;
import com.filterapi.exception.ClienteNaoEncontradoException;
import com.filterapi.exception.ClienteSemComprasException;
import com.filterapi.exception.ProdutoNaoEncontradoException;
import com.filterapi.model.ClienteCompra;
import com.filterapi.model.Compra;
import com.filterapi.model.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CompraServiceTest {

    @InjectMocks
    private CompraService compraService;

    @Mock
    private ProdutoClient produtoClient;

    @Mock
    private ClienteCompraClient clienteCompraClient;

    private List<Produto> produtos;
    private List<ClienteCompra> clientes;

    @BeforeEach
    void setUp() {
        produtos = new ArrayList<>();
        clientes = new ArrayList<>();
    }

    @Test
    void shouldGetCompras() {
        Produto produto = new Produto(1, "Tinto", 50.0, "2022", 2019);
        produtos.add(produto);

        ClienteCompra cliente = new ClienteCompra("João", "12345678900", List.of(new Compra("1", 2)));
        clientes.add(cliente);

        when(produtoClient.getProdutos()).thenReturn(produtos);
        when(clienteCompraClient.getClientesCompras()).thenReturn(clientes);

        List<Map<String, Object>> compras = compraService.getCompras();

        assertNotNull(compras);
        assertEquals(1, compras.size());
        assertEquals("João", compras.get(0).get("nome"));
        assertEquals("Tinto", ((Produto) compras.get(0).get("produto")).getTipoVinho());
        assertEquals(100.0, compras.get(0).get("valor_total"));

        verify(produtoClient, times(1)).getProdutos();
        verify(clienteCompraClient, times(1)).getClientesCompras();
    }

    @Test
    void shouldGetMaiorCompraAno() {
        Produto produto = new Produto(1, "Tinto", 50.0, "2022", 2019);
        produtos.add(produto);

        ClienteCompra cliente = new ClienteCompra("João", "12345678900", List.of(new Compra("1", 2)));
        clientes.add(cliente);

        when(produtoClient.getProdutos()).thenReturn(produtos);
        when(clienteCompraClient.getClientesCompras()).thenReturn(clientes);

        Map<String, Object> maiorCompra = compraService.getMaiorCompraAno(2019);

        assertNotNull(maiorCompra);
        assertEquals("João", maiorCompra.get("nome"));
        assertEquals("Tinto", ((Produto) maiorCompra.get("produto")).getTipoVinho());
        assertEquals(100.0, maiorCompra.get("valor_total"));

        verify(produtoClient, times(1)).getProdutos();
        verify(clienteCompraClient, times(1)).getClientesCompras();
    }

    @Test
    void shouldGetClientesFieis() {
        Produto produto = new Produto(1, "Tinto", 50.0, "2022", 2019);
        produtos.add(produto);

        ClienteCompra cliente = new ClienteCompra("João", "12345678900", List.of(new Compra("1", 2)));
        clientes.add(cliente);

        when(produtoClient.getProdutos()).thenReturn(produtos);
        when(clienteCompraClient.getClientesCompras()).thenReturn(clientes);

        List<Map<String, Object>> clientesFieis = compraService.getClientesFieis();

        assertNotNull(clientesFieis);
        assertEquals(1, clientesFieis.size());
        assertEquals("João", clientesFieis.get(0).get("nome"));
        assertEquals(100.0, clientesFieis.get(0).get("total_gasto"));

        verify(produtoClient, times(1)).getProdutos();
        verify(clienteCompraClient, times(1)).getClientesCompras();
    }

    @Test
    void shouldRecomendacaoVinhoSucesso() {
        Produto produto = new Produto(1, "Tinto", 50.0, "2022", 2019);
        produtos.add(produto);

        ClienteCompra cliente = new ClienteCompra("João", "12345678900", List.of(new Compra("1", 2)));
        clientes.add(cliente);

        when(produtoClient.getProdutos()).thenReturn(produtos);
        when(clienteCompraClient.getClientesCompras()).thenReturn(clientes);

        String recomendacao = compraService.recomendacaoVinho("12345678900");

        assertNotNull(recomendacao);
        assertEquals("Tinto", recomendacao);

        verify(produtoClient, times(1)).getProdutos();
        verify(clienteCompraClient, times(1)).getClientesCompras();
    }

    @Test
    void shouldRecomendacaoVinhoClienteNaoEncontrado() {
        when(clienteCompraClient.getClientesCompras()).thenReturn(Collections.emptyList());

        ClienteNaoEncontradoException exception = assertThrows(ClienteNaoEncontradoException.class, () -> {
            compraService.recomendacaoVinho("12345678900");
        });

        assertEquals("Cliente com CPF 12345678900 não encontrado.", exception.getMessage());

        verify(clienteCompraClient, times(1)).getClientesCompras();
    }

    @Test
    void shouldRecomendacaoVinhoClienteSemCompras() {
        ClienteCompra cliente = new ClienteCompra("João", "12345678900", new ArrayList<>());
        clientes.add(cliente);

        when(clienteCompraClient.getClientesCompras()).thenReturn(clientes);

        ClienteSemComprasException exception = assertThrows(ClienteSemComprasException.class, () -> {
            compraService.recomendacaoVinho("12345678900");
        });

        assertEquals("Cliente com CPF 12345678900 não fez compras.", exception.getMessage());

        verify(clienteCompraClient, times(1)).getClientesCompras();
    }

    @Test
    void shouldRecomendacaoVinhoProdutoNaoEncontrado() {
        ClienteCompra cliente = new ClienteCompra("João", "12345678900", List.of(new Compra("1", 2)));
        clientes.add(cliente);

        when(clienteCompraClient.getClientesCompras()).thenReturn(clientes);
        when(produtoClient.getProdutos()).thenReturn(Collections.emptyList());

        ProdutoNaoEncontradoException exception = assertThrows(ProdutoNaoEncontradoException.class, () -> {
            compraService.recomendacaoVinho("12345678900");
        });

        assertEquals("Produto com código 1 não encontrado.", exception.getMessage());

        verify(clienteCompraClient, times(1)).getClientesCompras();
        verify(produtoClient, times(1)).getProdutos();
    }
}
