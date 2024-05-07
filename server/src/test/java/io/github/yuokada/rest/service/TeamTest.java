package io.github.yuokada.rest.service;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.instancio.Instancio;

class TeamTest {

    public static void main(String[] args) {
        IntStream.range(0, 32)
            .mapToObj(n -> {
                return Instancio.of(Player.class)
                        .generate(field("id"), gen -> gen.ints().range(1, 2048))
                        .generate(field("backNumber"), gen -> gen.ints().range(0, 128).as(q -> q.toString()))
                        .create();
                }
            ).forEach(System.out::println);
    }

}