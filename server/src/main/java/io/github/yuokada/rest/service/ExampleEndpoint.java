package io.github.yuokada.rest.service;

import io.github.yuokada.rest.annotations.BasicAPI;
import io.github.yuokada.rest.util.DummyDataGenerator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.eclipse.microprofile.openapi.annotations.enums.Explode;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterStyle;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;
import org.jboss.resteasy.reactive.Separator;

@ApplicationScoped
@Path("/api/v1")
@BasicAPI
@SecuritySchemes(
    {
        @SecurityScheme(securitySchemeName = "basicAuth", type = SecuritySchemeType.HTTP, scheme = "basic"),
        @SecurityScheme(securitySchemeName = "jwt token", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER, scheme = "bearer", bearerFormat = "jwt")
    }

)
public class ExampleEndpoint implements ExampleEndpointApi {

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
    public Response players(
        @Parameter(schema = @Schema(type = SchemaType.NUMBER))
        @QueryParam("offset") int offset,
        @QueryParam("limit")
        @Parameter(schema = @Schema(type = SchemaType.NUMBER))
        @DefaultValue("50") int limit,
        @Parameter(
            in = ParameterIn.QUERY,
            description = "filter with team ids",
            style = ParameterStyle.FORM, explode = Explode.FALSE,
            schema = @Schema(type = SchemaType.ARRAY, implementation = Integer.class)
        )
        @Separator(",")
        @QueryParam("team_ids") Set<Integer> teamIds
    ) {
        Random random = new Random();
        System.out.println(teamIds);
        List<Player> players = DummyDataGenerator.getPlayers(random.nextInt(50), teamIds);
        return Response.ok(players)
            .build();
    }

    // endpoints for guest users
    @Override
    public Response testPlayers(
        @Parameter(description = "test header parameter")
        @DefaultValue("default-foo-value")
        @HeaderParam("X-Foo") String fooHeader,
        @Parameter(
            in = ParameterIn.QUERY,
            description = "Filter with team ids",
            style = ParameterStyle.FORM, explode = Explode.FALSE
            // , schema = @Schema(type = SchemaType.ARRAY, implementation = Integer.class)
        )
        @Separator(",")
        @QueryParam("team_ids") Set<Integer> teamIds
    ) {
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

