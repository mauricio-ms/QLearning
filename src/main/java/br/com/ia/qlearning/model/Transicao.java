package br.com.ia.qlearning.model;

import br.com.ia.qlearning.util.RecompensaConstantes;

import java.util.Objects;

public final class Transicao {

    private final Integer posicao;

    private final Double valor;

    private final Recompensa recompensa;

    private Transicao(final Integer posicao,
                      final Double valor,
                      final Recompensa recompensa) {
        this.posicao = posicao;
        this.valor = valor;
        this.recompensa = recompensa;
    }

    public static Transicao of(final Integer posicao,
                               final Double valor) {
        return new Transicao(posicao, valor, MapaRecompensas.criar().get(posicao));
    }

    public static Transicao of(final Integer posicao,
                               final Recompensa recompensa) {
        return new Transicao(posicao, 0D, recompensa);
    }

    public Transicao comValorAtualizado() {
        final Double metadeValorAtual = 0.5 * valor;
        final Double valorAtualizado = recompensa.getValor() + metadeValorAtual;
        return new Transicao(posicao, valorAtualizado, recompensa);
    }

    public Boolean isObjetivo() {
        return RecompensaConstantes.POSICAO_OBJETIVO.equals(posicao);
    }

    public Integer getPosicao() {
        return posicao;
    }

    public Double getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transicao transicao = (Transicao) o;
        return Objects.equals(posicao, transicao.posicao) &&
                Objects.equals(valor, transicao.valor) &&
                recompensa == transicao.recompensa;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posicao, valor, recompensa);
    }

    @Override
    public String toString() {
        return "Transicao{" +
                "posicao=" + posicao +
                ", valor=" + valor +
                ", recompensa=" + recompensa +
                '}';
    }
}