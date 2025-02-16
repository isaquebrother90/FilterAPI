package com.filterapi.client;

import com.filterapi.model.ClienteCompra;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "clienteCompraClient", url = "https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com")
public interface ClienteCompraClient {
    @GetMapping("/clientes-Vz1U6aR3GTsjb3W8BRJhcNKmA81pVh.json")
    List<ClienteCompra> getClientesCompras();
}
