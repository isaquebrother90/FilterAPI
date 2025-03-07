package com.filterapi.client;

import com.filterapi.model.Produto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "produtoClient", url = "https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com")
public interface ProdutoApiClient {
    @GetMapping("/produtos-mnboX5IPl6VgG390FECTKqHsD9SkLS.json")
    List<Produto> getProdutos();
}
