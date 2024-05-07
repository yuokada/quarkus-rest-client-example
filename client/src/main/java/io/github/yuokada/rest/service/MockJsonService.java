package io.github.yuokada.rest.service;

import io.quarkus.rest.client.reactive.ClientExceptionMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import org.jboss.logging.Logger;

@Path("/")
@RegisterRestClient(configKey = "my-json-server")
@Consumes("application/json")
@Produces(MediaType.APPLICATION_JSON)
public interface MockJsonService {
    static final Logger logger = Logger.getLogger(MockJsonService.class);

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
    List<Game> listGames();

    @ClientExceptionMapper
    static RuntimeException toException(Response response) {
        logger.warn(response.getStatus());
        if (response.getStatus() == 500) {
            return new RuntimeException("The remote service responded with HTTP 500");
        }
        return null;
    }
}
