package io.github.yuokada.rest.service;

import io.github.yuokada.rest.model.Game;
import io.github.yuokada.rest.model.Team;
import io.quarkus.test.junit.QuarkusTest;
import java.util.List;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class MockJsonServiceTest {

    // @InjectMock
    @RestClient
    MockJsonService mockJsonService;


    private static final Logger LOG = Logger.getLogger(MockJsonServiceTest.class);

    @Test
    public void testHelloEndpoint() {
        given()
            .when().get("/hello")
            .then()
            .statusCode(200)
            .body(is("Hello from RESTEasy Reactive"));
    }

    @Test
    public void testClient() {
        Team teams = mockJsonService.getTeamById(1);
        LOG.info(teams);
        assertNotNull(teams.urlPath);
        assertNotNull(teams.regulationAtBats);
        assertThat("size doesn't match", teams.id == 1);
    }

    @Test
    public void testTeamList() {
        List<Team> teams = mockJsonService.listTeams();
        LOG.info(teams);
        assertEquals(10, teams.size());
    }

    @Test
    public void testGameList() {
        List<Game> games = mockJsonService.listGames();
        LOG.info(games);
        assertEquals(5, games.size());
    }

    @Test
    public void testGameById() {
        Game game = mockJsonService.getGameById(1);
        LOG.info(game);
        LOG.info(game.date);
        assertEquals(1, game.id);
    }
}