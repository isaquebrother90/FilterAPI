package com.filterapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.filterapi.client.ClienteCompraApiClient;
import com.filterapi.client.ProdutoApiClient;
import com.filterapi.exception.ClienteNaoEncontradoException;
import com.filterapi.exception.ClienteSemComprasException;
import com.filterapi.exception.ProdutoNaoEncontradoException;
import com.filterapi.model.ClienteCompra;
import com.filterapi.model.Compra;
import com.filterapi.model.Produto;
import com.filterapi.service.Impl.CompraServiceImpl;
import com.filterapi.strategy.CompraStrategyContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CompraServiceImplTest {

    @InjectMocks
    private CompraServiceImpl compraServiceImpl;

    @Mock
    private ProdutoApiClient produtoApiClient;

    @Mock
    private ClienteCompraApiClient clienteCompraApiClient;

    @Mock
    private CompraStrategyContext strategyContext;

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

        when(strategyContext.executarEstrategia("ListarComprasStrategy")).thenReturn(Collections.singletonList(Map.of(
                "nome", "João",
                "produto", produto,
                "valor_total", 100.0
        )));

        List<Map<String, Object>> compras = compraServiceImpl.getCompras();

        assertNotNull(compras);
        assertEquals(1, compras.size());
        assertEquals("João", compras.get(0).get("nome"));
        assertEquals("Tinto", ((Produto) compras.get(0).get("produto")).getTipoVinho());
        assertEquals(100.0, compras.get(0).get("valor_total"));

        verify(strategyContext, times(1)).executarEstrategia("ListarComprasStrategy");
    }

    @Test
    void shouldGetMaiorCompraAno() {
        Produto produto = new Produto(1, "Tinto", 50.0, "2022", 2019);
        produtos.add(produto);

        ClienteCompra cliente = new ClienteCompra("João", "12345678900", List.of(new Compra("1", 2)));
        clientes.add(cliente);

        when(produtoApiClient.getProdutos()).thenReturn(produtos);
        when(clienteCompraApiClient.getClientesCompras()).thenReturn(clientes);

        Map<String, Object> maiorCompra = compraServiceImpl.getMaiorCompraAno(2019);

        assertNotNull(maiorCompra);
        assertEquals("João", maiorCompra.get("nome"));
        assertEquals("Tinto", ((Produto) maiorCompra.get("produto")).getTipoVinho());
        assertEquals(100.0, maiorCompra.get("valor_total"));

        verify(produtoApiClient, times(1)).getProdutos();
        verify(clienteCompraApiClient, times(1)).getClientesCompras();
    }

    @Test
    void shouldGetClientesFieis() {
        when(strategyContext.executarEstrategia("ClientesFieisStrategy")).thenReturn(Collections.singletonList(Map.of(
                "nome", "João",
                "total_gasto", 100.0
        )));

        List<Map<String, Object>> clientesFieis = compraServiceImpl.getClientesFieis();

        assertNotNull(clientesFieis);
        assertEquals(1, clientesFieis.size());
        assertEquals("João", clientesFieis.get(0).get("nome"));
        assertEquals(100.0, clientesFieis.get(0).get("total_gasto"));

        verify(strategyContext, times(1)).executarEstrategia("ClientesFieisStrategy");
    }

    @Test
    void shouldRecomendacaoVinhoSucesso() {
        Produto produto = new Produto(1, "Tinto", 50.0, "2022", 2019);
        produtos.add(produto);

        ClienteCompra cliente = new ClienteCompra("João", "12345678900", List.of(new Compra("1", 2)));
        clientes.add(cliente);

        when(produtoApiClient.getProdutos()).thenReturn(produtos);
        when(clienteCompraApiClient.getClientesCompras()).thenReturn(clientes);

        String recomendacao = compraServiceImpl.recomendacaoVinho("12345678900");

        assertNotNull(recomendacao);
        assertEquals("Tinto", recomendacao);

        verify(produtoApiClient, times(1)).getProdutos();
        verify(clienteCompraApiClient, times(1)).getClientesCompras();
    }

    @Test
    void shouldRecomendacaoVinhoClienteNaoEncontrado() {
        when(clienteCompraApiClient.getClientesCompras()).thenReturn(Collections.emptyList());

        ClienteNaoEncontradoException exception = assertThrows(ClienteNaoEncontradoException.class, () -> {
            compraServiceImpl.recomendacaoVinho("12345678900");
        });

        assertEquals("Cliente com CPF 12345678900 não encontrado.", exception.getMessage());

        verify(clienteCompraApiClient, times(1)).getClientesCompras();
    }

    @Test
    void shouldRecomendacaoVinhoClienteSemCompras() {
        ClienteCompra cliente = new ClienteCompra("João", "12345678900", new ArrayList<>());
        clientes.add(cliente);

        when(clienteCompraApiClient.getClientesCompras()).thenReturn(clientes);

        ClienteSemComprasException exception = assertThrows(ClienteSemComprasException.class, () -> {
            compraServiceImpl.recomendacaoVinho("12345678900");
        });

        assertEquals("Cliente com CPF 12345678900 não fez compras.", exception.getMessage());

        verify(clienteCompraApiClient, times(1)).getClientesCompras();
    }

    @Test
    void shouldRecomendacaoVinhoProdutoNaoEncontrado() {
        ClienteCompra cliente = new ClienteCompra("João", "12345678900", List.of(new Compra("1", 2)));
        clientes.add(cliente);

        when(clienteCompraApiClient.getClientesCompras()).thenReturn(clientes);
        when(produtoApiClient.getProdutos()).thenReturn(Collections.emptyList());

        ProdutoNaoEncontradoException exception = assertThrows(ProdutoNaoEncontradoException.class, () -> {
            compraServiceImpl.recomendacaoVinho("12345678900");
        });

        assertEquals("Produto com código 1 não encontrado.", exception.getMessage());

        verify(clienteCompraApiClient, times(1)).getClientesCompras();
        verify(produtoApiClient, times(1)).getProdutos();
    }
}
