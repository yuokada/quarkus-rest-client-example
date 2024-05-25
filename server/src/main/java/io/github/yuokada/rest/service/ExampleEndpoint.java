package io.github.yuokada.rest.service;

import io.github.yuokada.rest.annotations.BasicAPI;
import io.github.yuokada.rest.annotations.GuestAPI;
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
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirements;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.jboss.resteasy.reactive.NoCache;

@ApplicationScoped
@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
@BasicAPI
@SecuritySchemes(
    {
        @SecurityScheme(
            securitySchemeName = "basicAuth",
            type = SecuritySchemeType.HTTP,
            scheme = "basic"
        ),
        @SecurityScheme(
            securitySchemeName = "jwt token",
            type = SecuritySchemeType.APIKEY,
            in = SecuritySchemeIn.HEADER,
            scheme = "bearer",
            bearerFormat = "jwt"
        )
    }

)
public class ExampleEndpoint {

    @GET
    @Path("/teams")
    @Operation(
        summary = "Return a list of teams",
        description = "Return a list of teams"
    )
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
    @SecurityRequirements(
        {
            @SecurityRequirement(name = "basicAuth"),
            @SecurityRequirement(name = "jwt token")
        }
    )
    // @SecurityRequirement(name = "basicAuth")
    public Response listTeamRecord() {
        List<Team> teams = DummyDataGenerator.getTeamRecordList(12);
        return Response.ok(teams)
            .build();
    }

    @GET
    @Operation(
        summary = "Return a team detail",
        description = "Return a team detail"
    )
    @Path("/teams/{id:\\d+}")

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

    @GET
    @Path("/players")

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

    // endpoints for guest users
    @GET
    @Path("/guest/players")
    @GuestAPI
//    @APIResponses(
//        {
//            @APIResponse(
//                responseCode = "200",
//                description = "Returns a list of players",
//                content = @Content(
//                    schema = @Schema(
//                        type = SchemaType.ARRAY,
//                        implementation = Player.class)
//                )
//            )
//        }
//    )
    @APIResponseSchema(value = Player[].class,
        responseCode = "200",
        responseDescription = "Returns a list of players")
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

    @GET
    @Path("/guest/teams")
    @GuestAPI
    @APIResponseSchema(Team[].class)
    public Response testTeams() {
        Random random = new Random();
        var teams = DummyDataGenerator.getTeamRecordList(random.nextInt(50));
        return Response.ok(teams)
            .build();
    }

}

