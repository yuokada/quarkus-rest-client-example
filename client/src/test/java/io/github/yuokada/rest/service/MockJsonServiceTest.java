package io.github.yuokada.rest.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.quarkus.test.junit.QuarkusTest;
import java.util.List;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.ClientWebApplicationException;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class MockJsonServiceTest {

    private static final Logger logger = Logger.getLogger(MockJsonServiceTest.class);
    //@RestClient
    final private MockJsonService mockJsonService;

    public MockJsonServiceTest(@RestClient MockJsonService mockJsonService) {
        this.mockJsonService = mockJsonService;
        // RestClientBuilder.newBuilder().baseUri("http://localhost:8080").build(MockJsonService.class);
        // mockJsonService
    }

    @Test
    public void testClient() {
        Team teams = mockJsonService.getTeamById(1);
        logger.info(teams);
        assertNotNull(teams.urlPath());
        assertNotNull(teams.regulationAtBats());
        assertThat("size doesn't match", teams.id() == 1);
    }

    @Test
    public void testTeamList() {
        List<Team> teams = mockJsonService.listTeams();
        logger.info(teams);
        assertEquals(10, teams.size());
    }

    @Test
    public void testGameList() {
        List<Game> games = mockJsonService.listGames();
        logger.info(games);
        assertEquals(5, games.size());
    }

    @Test
    public void testGameById() {
        Game game = mockJsonService.getGameById(1);
        logger.info(game);
        logger.info(game.date());
        assertEquals(1, game.id());
    }

    @Test
    public void testGameByIdWith404() {
        try {
            Game game = mockJsonService.getGameById(100000);
            logger.info(game);
            logger.info(game.date());
            assertEquals(1, game.id());
        } catch (ClientWebApplicationException  e) {
            logger.error(e.getResponse().readEntity(String.class));
            logger.error(e.getMessage());
//            throw e;
        }
    }

}