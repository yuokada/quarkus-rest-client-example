package io.github.yuokada.rest.util;

import static org.instancio.Select.field;
import static org.instancio.Select.root;

import io.github.yuokada.rest.service.Player;
import io.github.yuokada.rest.service.Team;
import io.github.yuokada.rest.service.TeamRecord;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.instancio.Assign;
import org.instancio.Instancio;

public class InstancioGenerator {

    private static final Random gRandom = new Random();

    public static Team getTeam(Integer teamId) {
        return Instancio.of(Team.class)
            .set(field("id"), teamId)
            .generate(field("regulationAtBats"), gen -> gen.doubles().range(0.1, 2.0).nullable())
            .assign(Assign.valueOf(Team::getName)
                .to(Team::getUrlPath)
                .as((String teamName) -> teamName.toLowerCase())
            )
            .create();
    }

    public static List<Team> getTeamList(Integer size) {
        List<Team> teams = IntStream.range(1, size)
            .mapToObj(InstancioGenerator::getTeam)
            .collect(Collectors.toList());
        return teams;
    }

    public static Player getPlayer(Integer teamId) {
        Team team = getTeam(teamId);
        return Instancio.of(Player.class)
            .generate(field("id"), gen -> gen.ints().range(1, 1024))
            .set(field("team"), team)
            .supply(field("backNumber"), InstancioGenerator::backNumberGenerator)
            .create();
    }

    public static Player getPlayer(Team team, Integer id) {
        return Instancio.of(Player.class)
            .set(field("id"), id)
            .set(field("team"), team)
            .supply(field("backNumber"), InstancioGenerator::backNumberGenerator)
            .create();
    }

    public static List<Player> getPlayers(Integer size) {
        List<Team> teams = getTeamList(6);
        List<Player> players = IntStream.range(1, size)
            .mapToObj(i -> {
                return Instancio.of(Player.class)
                    .generate(field("id"), gen -> gen.ints().range(1, 1024))
                    .generate(field("team"), gen -> gen.oneOf(teams))
                    .supply(field("backNumber"), InstancioGenerator::backNumberGenerator)
                    .create();
            })
            .collect(Collectors.toList());
        return players;
    }

    private static String backNumberGenerator() {
        int n = gRandom.nextInt(128);
        return String.format("%d", n);
    }

    // Methods for TeamRecord
    public static List<TeamRecord> getTeamRecordList(Integer size) {
        List<TeamRecord> teams = Instancio.ofList(TeamRecord.class)
            .size(size)
            .generate(field(TeamRecord::id), gen -> gen.ints().range(1, 128))
            .generate(field(TeamRecord::regulationAtBats), gen -> gen.doubles().range(0.1, 2d))
            .create();
        return teams;
    }
    public static TeamRecord getTeamRecord(Integer teamId) {
        return Instancio.of(TeamRecord.class)
             .set(field("id"), teamId)
             .generate(field(TeamRecord::regulationAtBats), gen ->
                 gen.doubles().range(0.1, 2d)
             )
            .create();
    }

}
