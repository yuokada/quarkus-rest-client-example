package io.github.yuokada.rest.service;

import io.github.yuokada.rest.annotations.AuthErrorResponse;
import io.github.yuokada.rest.util.DummyDataGenerator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Random;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.servers.Servers;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

@ApplicationScoped
@Path("/api/v1")
@Tags({
    @Tag(name = "BMS basic API", description = "Common APIs for BMS")
})
@Servers({
    @Server(url = "http://localhost:8080", description = "Local server")
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
            ),
        }
    )
    public Response listTeamRecord() {
        List<Team> teams = DummyDataGenerator.getTeamRecordList(12);
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
                description = "Returns the detail of a team",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(type = SchemaType.OBJECT, implementation = Team.class)
                )
            )
        }
    )
    public Response detailTeamRecord(@PathParam("id") Integer teamId) {
        var team = DummyDataGenerator.getTeamRecord(teamId);
        return Response.ok(team)
            .build();
    }

    ////
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
        List<Player> players = DummyDataGenerator.getPlayers(random.nextInt(50));
        return Response.ok(players)
            .build();
    }

    // Test endpoints
    @GET
    @Path("/experimental/player")
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
    public Response testPlayers(
        @Parameter(description = "test header parameter")
        @DefaultValue("default-foo-value")
        @HeaderParam("X-Foo") String fooHeader
    ) {
        Random random = new Random();
        List<Player> players = DummyDataGenerator.getPlayers(random.nextInt(50));
        return Response.ok(players)
            .build();
    }


}

