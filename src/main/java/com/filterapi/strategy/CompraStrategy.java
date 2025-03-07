package com.filterapi.strategy;

import java.util.List;
import java.util.Map;

public interface CompraStrategy {
  List<Map<String, Object>> calcular();
}
