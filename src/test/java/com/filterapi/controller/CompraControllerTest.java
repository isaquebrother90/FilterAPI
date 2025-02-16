package com.filterapi.controller;

import com.filterapi.service.Impl.CompraServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CompraControllerTest {

    @InjectMocks
    private CompraController compraController;

    @Mock
    private CompraServiceImpl compraServiceImpl;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(compraController).build();
    }

    @Test
    void shouldGetComprasWithSucess() throws Exception {
        List<Map<String, Object>> compras = List.of(
                Map.of("produto", "Vinho Tinto", "quantidade", 3),
                Map.of("produto", "Vinho Branco", "quantidade", 5)
        );

        when(compraServiceImpl.getCompras()).thenReturn(compras);

        mockMvc.perform(get("/api/compras"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].produto").value("Vinho Tinto"))
                .andExpect(jsonPath("$[1].produto").value("Vinho Branco"))
                .andExpect(jsonPath("$[0].quantidade").value(3))
                .andExpect(jsonPath("$[1].quantidade").value(5));

        verify(compraServiceImpl, times(1)).getCompras();
    }

    @Test
    void shouldGetMaiorCompraAnoWithSuccess() throws Exception {
        Map<String, Object> maiorCompra = Map.of(
                "nome", "João",
                "cpf", "12345678900",
                "produto", "Vinho Tinto",
                "quantidade", 5,
                "valor_total", 100.0
        );

        when(compraServiceImpl.getMaiorCompraAno(2022)).thenReturn(maiorCompra);

        mockMvc.perform(get("/api/maior-compra/2022"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.produto").value("Vinho Tinto"))
                .andExpect(jsonPath("$.valor_total").value(100.0));

        verify(compraServiceImpl, times(1)).getMaiorCompraAno(2022);
    }

    @Test
    void shouldGetClientesFieisWithSuccess() throws Exception {
        List<Map<String, Object>> clientesFieis = List.of(
                Map.of("nome", "Maria", "cpf", "98765432100", "total_gasto", 500.0),
                Map.of("nome", "João", "cpf", "12345678900", "total_gasto", 300.0)
        );

        when(compraServiceImpl.getClientesFieis()).thenReturn(clientesFieis);

        mockMvc.perform(get("/api/clientes-fieis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Maria"))
                .andExpect(jsonPath("$[0].total_gasto").value(500.0));

        verify(compraServiceImpl, times(1)).getClientesFieis();
    }

    @Test
    void shouldGetRecomendacaoWithSuccess() throws Exception {
        String cpf = "12345678900";
        String recomendacao = "Vinho Tinto";

        when(compraServiceImpl.recomendacaoVinho(cpf)).thenReturn(recomendacao);

        mockMvc.perform(get("/api/recomendacao/{cpf}", cpf))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Vinho Tinto"));

        verify(compraServiceImpl, times(1)).recomendacaoVinho(cpf);
    }
}
