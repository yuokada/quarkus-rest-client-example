package io.github.yuokada.rest.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import org.junit.jupiter.api.Test;

class DummyDataGeneratorTest {

    @Test
    void getPlayers() {
        Set<Integer> teamIds = Set.of(1, 7);
        var players = DummyDataGenerator.getPlayers(teamIds);

        // If no size is specified, a collection of random size (between 2 and 6 inclusive) will be generated.
        // see: https://www.instancio.org/user-guide/#creating-collections
        assertTrue(players.size() <= 6);
    }

//    @Test
//    void testGetPlayers() {
//    }
//
//    @Test
//    void getPlayersByTeam() {
//    }
}