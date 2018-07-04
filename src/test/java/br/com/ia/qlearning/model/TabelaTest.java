package br.com.ia.qlearning.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabelaTest {

    @Test
    public void getMelhorTransicaoDeveRetornarTransicaoComMaiorValor() {

        final Map<Integer, List<Transicao>> q = new HashMap<>();
        q.put(18, Arrays.asList(Transicao.of(13, 0.0),
                Transicao.of(17, 4.0),
                Transicao.of(19, 5.0),
                Transicao.of(23, 3.0)));
        final Tabela tabela = Tabela.of(q);

        final Transicao melhorTransicao = tabela.getMelhorTransicao(18);
        Assert.assertEquals(Transicao.of(19, 5.0), melhorTransicao);
    }

    @Test(expected = RuntimeException.class)
    public void getMelhorTransicaoParaEstadoInexistente() {

        final Map<Integer, List<Transicao>> q = new HashMap<>();
        q.put(0, Arrays.asList(Transicao.of(1, 0.0)));
        final Tabela tabela = Tabela.of(q);

        tabela.getMelhorTransicao(2);
    }

    @Test
    public void _() {
        final Map<Integer, List<Transicao>> q = new HashMap<>();
        q.put(40, Arrays.asList(Transicao.of(35, 0.0),
                Transicao.of(41, 0.0),
                Transicao.of(45, Recompensa.OBJETIVO)));
        q.put(45, Arrays.asList(Transicao.of(40, Recompensa.INTRANSPONIVEL),
                Transicao.of(46, 0.0)));

        final Tabela tabela = Tabela.of(q);
        tabela.atualizarTransicao(40);
        final List<Transicao> transicoes = tabela.getQ().get(40);
        final Transicao transicao = transicoes.stream().filter(t -> t.getPosicao().equals(45))
                .findFirst().orElse(null);
        Assert.assertEquals((Double) 100D, transicao.getValor());
    }
}