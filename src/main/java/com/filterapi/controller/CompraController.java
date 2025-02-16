package com.filterapi.controller;

import com.filterapi.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping("/compras")
    public List<Map<String, Object>> getCompras() {
        return compraService.getCompras();
    }

    @GetMapping("/maior-compra/{ano}")
    public Map<String, Object> getMaiorCompra(@PathVariable int ano) {
        return compraService.getMaiorCompraAno(ano);
    }

    @GetMapping("/clientes-fieis")
    public List<Map<String, Object>> getClientesFieis() {
        return compraService.getClientesFieis();
    }

    @GetMapping("/recomendacao/{cpf}")
    public String getRecomendacao(@PathVariable String cpf) {
        return compraService.recomendacaoVinho(cpf);
    }
}
