package br.com.ia.qlearning;

import br.com.ia.qlearning.model.Tabela;
import org.junit.Test;

public class QLearningTest {

    @Test
    public void test() {

        QLearning qLearning = new QLearning();
        qLearning.episodio();

        final Tabela tabela = qLearning.getTabela();
        System.out.println(tabela);

        tabela.getQ().entrySet().forEach(es -> System.out.println(String.format("%s:%s", es.getKey(), es.getValue())));
    }
}