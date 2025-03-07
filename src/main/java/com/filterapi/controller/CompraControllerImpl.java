package com.filterapi.controller;

import com.filterapi.service.CompraService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "FilterAPI", description = "API para filtro e geração de recomendação e relatórios de compras")
public class CompraControllerImpl implements CompraController {

    private final CompraService compraService;

    public CompraControllerImpl(CompraService compraService) {
        this.compraService = compraService;
    }

    @Override
    @GetMapping("/compras")
    public List<Map<String, Object>> getCompras() {
        return compraService.getCompras();
    }

    @Override
    @GetMapping("/maior-compra/{ano}")
    public Map<String, Object> getMaiorCompra(@PathVariable int ano) {
        return compraService.getMaiorCompraAno(ano);
    }

    @Override
    @GetMapping("/clientes-fieis")
    public List<Map<String, Object>> getClientesFieis() {
        return compraService.getClientesFieis();
    }

    @Override
    @GetMapping("/recomendacao/{cpf}")
    public String getRecomendacao(@PathVariable String cpf) {
        return compraService.recomendacaoVinho(cpf);
    }
}
