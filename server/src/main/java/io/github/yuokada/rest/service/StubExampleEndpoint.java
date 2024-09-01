package io.github.yuokada.rest.service;

import io.github.yuokada.rest.annotations.BasicAPI;
import io.github.yuokada.rest.util.DummyDataGenerator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;

@ApplicationScoped
@Path("/api/v1")
@BasicAPI
@SecuritySchemes(
    {
        @SecurityScheme(securitySchemeName = "basicAuth", type = SecuritySchemeType.HTTP, scheme = "basic"),
        @SecurityScheme(securitySchemeName = "jwt token", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER, scheme = "bearer", bearerFormat = "jwt")
    }

)
public class StubExampleEndpoint implements ExampleEndpointApi {

    @Override
    public Response listTeamRecord() {
        List<Team> teams = DummyDataGenerator.getTeamRecordList(12);
        return Response.ok(teams)
            .build();
    }

    @Override
    public Response detailTeamRecord(Integer teamId) {
        var team = DummyDataGenerator.getTeamRecord(teamId);
        return Response.ok(team)
            .build();
    }

    @Override
    public Response listPlayersOfTheTeam(Integer teamId) {
        Random random = new Random();
        List<Player> players = DummyDataGenerator.getPlayersByTeam(random.nextInt(50), teamId);

        return Response.ok(players).build();
    }

    @Override
    public Response players(int offset, int limit, Set<Integer> teamIds) {
        Random random = new Random();
        System.out.println(teamIds);
        List<Player> players = DummyDataGenerator.getPlayers(random.nextInt(50), teamIds);
        return Response.ok(players)
            .build();
    }

    // endpoints for guest users
    @Override
    public Response testPlayers(String fooHeader, Set<Integer> teamIds) {
        Random random = new Random();
        System.out.println(teamIds);
        List<Player> players = DummyDataGenerator.getPlayers(random.nextInt(50), teamIds);
        return Response.ok(players)
            .build();
    }

    @Override
    public Response testTeams() {
        Random random = new Random();
        var teams = DummyDataGenerator.getTeamRecordList(random.nextInt(50));
        return Response.ok(teams)
            .build();
    }

}

