package br.com.ia.qlearning;

import br.com.ia.qlearning.model.Tabela;
import br.com.ia.qlearning.model.Transicao;

import java.util.ArrayList;
import java.util.List;

public final class QLearning {

    private final Tabela tabela;

    private final List<Transicao> caminhoEpisodio;

    public QLearning() {
        tabela = Tabela.create();
        caminhoEpisodio = new ArrayList<>();
    }

    public void episodio() {
//        do {
//            transicaoAtualizada = tabela.atualizarTransicao(s);
//            s = transicaoAtualizada.getPosicao();
//        } while (!transicaoAtualizada.isObjetivo());

        Integer s = 0;
        Transicao transicaoAtualizada = Transicao.of(0, 0.0);
        while(!transicaoAtualizada.isObjetivo()) {
            caminhoEpisodio.add(transicaoAtualizada);
            transicaoAtualizada = tabela.atualizarTransicao(s);
            System.out.println(transicaoAtualizada);
            s = transicaoAtualizada.getPosicao();
        }
        System.out.println("END");
    }

    public Tabela getTabela() {
        return tabela;
    }

    public List<Transicao> getCaminhoEpisodio() {
        return caminhoEpisodio;
    }
}