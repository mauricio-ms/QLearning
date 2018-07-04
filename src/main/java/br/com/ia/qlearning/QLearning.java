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
        caminhoEpisodio.clear();
        Integer s = 0;
        Transicao transicaoAtualizada = Transicao.of(s, 0.0);
        while(!transicaoAtualizada.isObjetivo()) {
            caminhoEpisodio.add(transicaoAtualizada);
            transicaoAtualizada = tabela.atualizarTransicao(s);
            s = transicaoAtualizada.getPosicao();
        }
    }

    public Tabela getTabela() {
        return tabela;
    }

    public List<Transicao> getCaminhoEpisodio() {
        return caminhoEpisodio;
    }
}