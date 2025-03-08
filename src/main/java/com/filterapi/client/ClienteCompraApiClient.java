package com.filterapi.client;

import com.filterapi.model.ClienteCompra;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/** Esta classe requisita compras de cada cliente de um enpoint externo. */
@FeignClient(
    name = "clienteCompraClient",
    url = "https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com")
public interface ClienteCompraApiClient {
  @GetMapping("/clientes-Vz1U6aR3GTsjb3W8BRJhcNKmA81pVh.json")
  List<ClienteCompra> getClientesCompras();
}
