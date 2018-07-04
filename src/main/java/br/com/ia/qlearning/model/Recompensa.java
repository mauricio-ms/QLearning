package br.com.ia.qlearning.model;

public enum Recompensa {

    INICIAl(-1),
    INTRANSPONIVEL(-100),
    CONVENCIONAL(-1),
    OBJETIVO(100);

    private final Integer valor;

    private Recompensa(final Integer valor) {
        this.valor = valor;
    }

    public Integer getValor() {
        return valor;
    }
}