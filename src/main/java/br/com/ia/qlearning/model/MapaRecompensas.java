package br.com.ia.qlearning.model;

import br.com.ia.qlearning.util.RecompensaConstantes;

import java.util.HashMap;
import java.util.Map;

public final class MapaRecompensas {

    private final Map<Integer, Recompensa> mapaRecompensas;

    private MapaRecompensas() {
        mapaRecompensas = gerarMapaRecompensas();
    }

    public static MapaRecompensas criar() {
        return new MapaRecompensas();
    }

    private Map<Integer, Recompensa> gerarMapaRecompensas() {
        final Map<Integer, Recompensa> mapaRecompensas = new HashMap<>();
        RecompensaConstantes.INTRANSPONIVEIS_INDICES.forEach(i -> mapaRecompensas.put(i, Recompensa.INTRANSPONIVEL));
        RecompensaConstantes.CONVENCIONAIS_INDICES.forEach(i -> mapaRecompensas.put(i, Recompensa.CONVENCIONAL));
        mapaRecompensas.put(RecompensaConstantes.POSICAO_INICIAL, Recompensa.INICIAl);
        mapaRecompensas.put(RecompensaConstantes.POSICAO_OBJETIVO, Recompensa.OBJETIVO);
        return mapaRecompensas;
    }

    public Recompensa get(final Integer i) {
        return mapaRecompensas.get(i);
    }

    @Override
    public String toString() {
        return "MapaRecompensas{" +
                "mapaRecompensas=" + mapaRecompensas +
                '}';
    }
}