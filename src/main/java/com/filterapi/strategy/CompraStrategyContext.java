package com.filterapi.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompraStrategyContext {

    private final Map<String, CompraStrategy> strategies = new HashMap<>();

    @Autowired
    public CompraStrategyContext(List<CompraStrategy> strategyList) {
        for (CompraStrategy strategy : strategyList) {
            strategies.put(strategy.getClass().getSimpleName(), strategy);
        }
    }

    public List<Map<String, Object>> executarEstrategia(String estrategia) {
        CompraStrategy strategy = strategies.get(estrategia);
        if (strategy == null) {
            throw new IllegalArgumentException("Estratégia desconhecida: " + estrategia);
        }
        return strategy.calcular();
    }
}
