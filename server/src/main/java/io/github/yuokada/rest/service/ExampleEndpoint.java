package io.github.yuokada.rest.service;

import io.github.yuokada.rest.util.InstancioGenerator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

@ApplicationScoped
@Path("/api/v1")
@Tags({
    @Tag(name = "BMS basic API", description = "Common APIs for BMS")
})
public class ExampleEndpoint {

    @GET
    @Path("/team")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(
        {
            @APIResponse(
                responseCode = "200",
                description = "Returns a list of teams",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(type = SchemaType.ARRAY, implementation = Team.class)
                )
            )
        }
    )
    public Response listTeam() {
        Random random = new Random();
        List<Team> teams = IntStream.range(1, random.nextInt(50))
            .mapToObj(InstancioGenerator::getTeam)
            .collect(Collectors.toList());
        List<Team> teams1 = InstancioGenerator.getTeamList(12);
        return Response.ok(teams)
            .build();
    }

    @GET
    @Path("/team/{id:\\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(
        {
            @APIResponse(
                responseCode = "200",
                description = "Returns the detail of a teams",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(type = SchemaType.OBJECT, implementation = Team.class)
                )
            )
        }
    )
    public Response detailTeam(@PathParam("id") Integer teamId) {
        Team team = InstancioGenerator.getTeam(teamId);
        return Response.ok(team)
            .build();
    }

    @GET
    @Path("/player")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(
        {
            @APIResponse(
                responseCode = "200",
                description = "Returns a list of players",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        type = SchemaType.ARRAY,
                        implementation = Player.class)
                )
            )
        }
    )
    public Response players() {
        Random random = new Random();
        List<Player> players = InstancioGenerator.getPlayers(random.nextInt(50));
        return Response.ok(players)
            .build();
    }
}

