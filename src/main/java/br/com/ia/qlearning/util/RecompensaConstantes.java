package br.com.ia.qlearning.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class RecompensaConstantes {

    public static final List<Integer> INTRANSPONIVEIS_INDICES = Stream.of(5, 8, 10, 13, 15, 16, 20, 23, 24, 25,
            28, 29, 30, 35, 36, 38, 40).collect(Collectors.toList());

    public static final List<Integer> CONVENCIONAIS_INDICES = Stream.of(1, 2, 3, 4, 6, 7, 9, 11, 12, 14, 17, 18, 19,
            21, 22, 26, 27, 31, 32, 33, 34, 37, 39, 41, 42, 43, 44, 46, 47, 48, 49).collect(Collectors.toList());

    public static final Integer POSICAO_INICIAL = 0;

    public static final Integer POSICAO_OBJETIVO = 45;

    private RecompensaConstantes() {
    }
}