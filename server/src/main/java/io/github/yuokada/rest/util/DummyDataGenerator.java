package io.github.yuokada.rest.util;

import static org.instancio.Select.field;

import io.github.yuokada.rest.service.Player;
import io.github.yuokada.rest.service.Team;
import io.github.yuokada.rest.service.TeamLegacy;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.datafaker.Faker;
import org.instancio.Assign;
import org.instancio.Instancio;

public class DummyDataGenerator {

    private static final Random gRandom = new Random();
    private static final Faker gFaker = new Faker();

    @Deprecated
    public static TeamLegacy getTeam(Integer teamId) {
        return Instancio.of(TeamLegacy.class)
            .set(field("id"), teamId)
            .set(field("name"), gFaker.team().name())
            .generate(
                field("regulationAtBats"),
                gen -> gen.doubles().range(0.1, 2.5)
                    .as(d -> Double.valueOf(String.format("%.1f", d))))
            .assign(
                Assign.valueOf(TeamLegacy::getName)
                    .to(TeamLegacy::getUrlPath)
                    .as((String teamName) -> teamName.toLowerCase().replace(" ", "-")))
            .create();
    }

    public static List<Player> getPlayers(Integer size, Set<Integer> teamIds) {
        System.out.println(teamIds);
        List<Team> records;
        if (teamIds.isEmpty()) {
            records = getTeamRecordList(6);
        } else {
            records = teamIds.stream().map(DummyDataGenerator::getTeamRecord)
                .collect(Collectors.toList());
            System.out.println(records);
        }
        var nameFaker = new Faker(Locale.JAPAN).name();

        List<Player> players = IntStream.range(1, size)
            .mapToObj(i -> Instancio.of(Player.class)
                .generate(field("id"), gen -> gen.ints().range(1, 1024))
                .generate(field("team"), gen -> gen.oneOf(records))
                .set(field("name"), nameFaker.fullName())
                .supply(field("backNumber"), DummyDataGenerator::backNumberGenerator)
                .create())
            .collect(Collectors.toList());
        return players;
    }

    private static String backNumberGenerator() {
        return String.format("%d", gRandom.nextInt(110));
    }

    // Methods for TeamRecord
    public static List<Team> getTeamRecordList(Integer size) {
        List<Team> teams = Instancio.ofList(Team.class)
            .size(size)
            .generate(field(Team::id), gen -> gen.ints().range(1, 128))
            .set(field(Team::name), gFaker.team().name())
            .generate(field(Team::regulationAtBats),
                gen -> gen.doubles().range(0.1, 2.5).as(
                    d -> Double.valueOf(String.format("%.1f", d))
                ))
            .create();
        return teams;
    }

    public static Team getTeamRecord(Integer teamId) {
        return Instancio.of(Team.class)
            // .set(field("id"), teamId)
            .set(field(Team::id), teamId)
            .set(field(Team::name), gFaker.team().name())
            .generate(field(Team::regulationAtBats), gen ->
                gen.doubles().range(0.1, 2.5).as(
                    d -> Double.valueOf(String.format("%.1f", d))
                )
            )
            .create();
    }

}
