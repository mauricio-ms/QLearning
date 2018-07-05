package br.com.ia.qlearning.model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Tabela {

    private final MapaRecompensas mapaRecompensas;

    private final Map<Integer, List<Transicao>> q;

    private Tabela(final Map<Integer, List<Transicao>> q) {
        mapaRecompensas = MapaRecompensas.criar();
        this.q = q;
    }

    private Tabela() {
        mapaRecompensas = MapaRecompensas.criar();
        this.q = gerarQ();
    }

    public static Tabela of(final Map<Integer, List<Transicao>> q) {
        return new Tabela(q);
    }

    public static Tabela create() {
        return new Tabela();
    }

    // TODO - 80 % Das vezes, verificar forma de implementar
    public Transicao getMelhorTransicao(final Integer s) {
        final List<Transicao> transicoesDisponiveis = getTransicoesDisponiveis(s);

        final Map<Double, List<Transicao>> groupedByValor = transicoesDisponiveis.stream()
                .collect(Collectors.groupingBy(Transicao::getValor, TreeMap::new, Collectors.toList()));

        final Set<Double> doubles = groupedByValor.keySet();
        final Double keyMaiorValor = doubles.stream().reduce((first, second) -> second)
                .orElseThrow(() -> new RuntimeException(String.format("Estado %s não existe na tabela q", s)));

        final List<Transicao> transicoes = groupedByValor.get(keyMaiorValor);
        return transicoes.get(new Random().nextInt(transicoes.size()));
    }

    private List<Transicao> getTransicoesDisponiveis(final Integer s) {
        return q.getOrDefault(s, new ArrayList<>());
    }

    public Transicao atualizarTransicao(final Integer s) {
        final Transicao melhorTransicao = getMelhorTransicao(s);
        final Transicao melhorTransicaoParaMelhorTransicao = getMelhorTransicao(melhorTransicao.getPosicao());
        final Transicao transicaoComValorAtualizado =
                melhorTransicao.comValorAtualizado(melhorTransicaoParaMelhorTransicao);

        final List<Transicao> transicoes = q.get(s);
        transicoes.set(transicoes.indexOf(melhorTransicao),
                transicaoComValorAtualizado);
        q.put(s, transicoes);

        return transicaoComValorAtualizado;
    }

    private Map<Integer, List<Transicao>> gerarQ() {
        final Integer[] vetorValores = gerarVetorValores();

        final Map<Integer, List<Transicao>> q = new HashMap<>();
        for (int i = 0; i < vetorValores.length; i++) {
            final Boolean isCantoEsquerdo = i >= 1 && i <= 3;
            final Boolean isCantoDireito = i >= 46 && i <= 48;
            final Boolean isCantoInferior = i > 0 && i % 5 == 0 && i < 45;
            final Boolean isCantoSuperior = i > 4 && (i - 9) % 5 == 0 && i < 49;
            final Boolean isCantoInferiorEsquerdo = i == 0;
            final Boolean isCantoInferiorDireito = i == 45;
            final Boolean isCantoSuperiorEsquerdo = i == 4;
            final Boolean isCantoSuperiorDireito = i == 49;

            final Integer s = vetorValores[i];
            if (isCantoEsquerdo) {
                populaDeCantoEsquerdo(q, s);
            } else if (isCantoDireito) {
                populaDeCantoDireito(q, s);
            } else if (isCantoInferior) {
                populaDeCantoInferior(q, s);
            } else if (isCantoSuperior) {
                populaDeCantoSuperior(q, s);
            } else if (isCantoInferiorEsquerdo) {
                populaDeCantoInferiorEsquerdo(q, s);
            } else if (isCantoInferiorDireito) {
                populaDeCantoInferiorDireito(q, s);
            } else if (isCantoSuperiorEsquerdo) {
                populaDeCantoSuperiorEsquerdo(q, s);
            } else if (isCantoSuperiorDireito) {
                populaDeCantoSuperiorDireito(q, s);
            } else {
                populaTodasDirecoes(q, s);
            }
        }
        return q;
    }

    private void populaTodasDirecoes(final Map<Integer, List<Transicao>> q,
                                     final Integer s) {
        q.put(s, criarTransicoes(Stream.of(s - 5, s + 1, s + 5, s - 1)));

    }

    private void populaDeCantoEsquerdo(final Map<Integer, List<Transicao>> q,
                                       final Integer s) {
        q.put(s, criarTransicoes(Stream.of(s - 1, s + 1, s + 5)));
    }

    private void populaDeCantoDireito(final Map<Integer, List<Transicao>> q,
                                      final Integer s) {
        q.put(s, criarTransicoes(Stream.of(s - 1, s + 1, s - 5)));
    }

    private void populaDeCantoInferior(final Map<Integer, List<Transicao>> q,
                                       final Integer s) {
        q.put(s, criarTransicoes(Stream.of(s - 5, s + 1, s + 5)));
    }

    private void populaDeCantoSuperior(final Map<Integer, List<Transicao>> q,
                                       final Integer s) {
        q.put(s, criarTransicoes(Stream.of(s - 5, s - 1, s + 5)));
    }

    private void populaDeCantoInferiorEsquerdo(final Map<Integer, List<Transicao>> q,
                                               final Integer s) {
        q.put(s, criarTransicoes(Stream.of(s + 1, s + 5)));
    }

    private void populaDeCantoInferiorDireito(final Map<Integer, List<Transicao>> q,
                                              final Integer s) {
        q.put(s, criarTransicoes(Stream.of(s - 5, s + 1)));
    }

    private void populaDeCantoSuperiorEsquerdo(final Map<Integer, List<Transicao>> q,
                                               final Integer s) {
        q.put(s, criarTransicoes(Stream.of(s - 1, s + 5)));
    }

    private void populaDeCantoSuperiorDireito(final Map<Integer, List<Transicao>> q,
                                              final Integer s) {
        q.put(s, criarTransicoes(Stream.of(s - 1, s - 5)));
    }

    private List<Transicao> criarTransicoes(final Stream<Integer> valores) {
        return valores.map(i -> Transicao.of(i, mapaRecompensas.get(i))).collect(Collectors.toList());
    }

    private Integer[] gerarVetorValores() {
        final Integer[] vetorValores = new Integer[50];
        for (int i = 0; i < 50; i++) {
            vetorValores[i] = i;
        }
        return vetorValores;
    }

    public Map<Integer, List<Transicao>> getQ() {
        return q;
    }

    public MapaRecompensas getMapaRecompensas() {
        return mapaRecompensas;
    }

    @Override
    public String toString() {
        return "Tabela{" +
                "mapaRecompensas=" + mapaRecompensas +
                ", q=" + q +
                '}';
    }
}