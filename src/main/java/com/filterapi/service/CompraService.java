package com.filterapi.service;

import java.util.List;
import java.util.Map;

public interface CompraService {
  List<Map<String, Object>> getCompras();

  Map<String, Object> getMaiorCompraAno(int ano);

  List<Map<String, Object>> getClientesFieis();

  String recomendacaoVinho(String cpf);
}
