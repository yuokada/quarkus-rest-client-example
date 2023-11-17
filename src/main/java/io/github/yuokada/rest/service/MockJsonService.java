package io.github.yuokada.rest.service;

import io.github.yuokada.rest.model.Game;
import io.github.yuokada.rest.model.Team;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/")
@RegisterRestClient(configKey = "my-json-server")
public interface MockJsonService {

    @GET
    @Path("/teams/{id}")
    Team getTeamById(@PathParam("id") Integer id);

    @GET
    @Path("/teams")
    List<Team> listTeams();

    @GET
    @Path("/games/{id}")
    Game getGameById(@PathParam("id") Integer id);

    @GET
    @Path("/games")
    @Produces(MediaType.APPLICATION_JSON)
    List<Game> listGames();
}
