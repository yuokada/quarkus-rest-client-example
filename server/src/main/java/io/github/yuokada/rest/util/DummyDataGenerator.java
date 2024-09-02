package io.github.yuokada.rest.util;

import static org.instancio.Select.field;

import io.github.yuokada.rest.service.Player;
import io.github.yuokada.rest.service.Team;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.datafaker.Faker;
import org.instancio.Assign;
import org.instancio.Instancio;
import org.instancio.Model;

public class DummyDataGenerator {

    private static final Random gRandom = new Random();
    private static final Faker gFaker = new Faker();

    private static Model<Player> providePlayerModel() {
        return Instancio.of(Player.class)
            .generate(field("id"), gen -> gen.ints().range(1, 1024))
            .generate(field("team"), gen -> gen.oneOf(getTeamRecordList(6)))
            .set(field("name"), gFaker.name().fullName())
            .supply(field("backNumber"), DummyDataGenerator::backNumberGenerator)
            .toModel();
    }

    public static List<Player> getPlayers(Set<Integer> teamIds) {
        List<Team> records;
        if (teamIds.isEmpty()) {
            records = getTeamRecordList(6);
        } else {
            records = teamIds.stream().map(DummyDataGenerator::getTeamRecord)
                .collect(Collectors.toList());
        }
        Model<Player> playerModel = providePlayerModel();
        List<Player> players = Instancio.ofList(playerModel)
            // .size(size)
            .generate(field(Player::team), gen -> gen.oneOf(records))
            .create();
//        players = Instancio.of(playerModel)
//            .generate(field(Player::team), gen -> gen.oneOf(records))
//            .stream()
//            .limit(size)
//            .collect(Collectors.toList());
        return players;
    }

    public static List<Player> getPlayers(Integer size, Set<Integer> teamIds) {
        List<Team> records;
        if (teamIds.isEmpty()) {
            records = getTeamRecordList(6);
        } else {
            records = teamIds.stream().map(DummyDataGenerator::getTeamRecord)
                .collect(Collectors.toList());
        }
        var nameFaker = new Faker(Locale.JAPAN).name();

        return IntStream.range(1, size)
            .mapToObj(i -> Instancio.of(Player.class)
                .generate(field("id"), gen -> gen.ints().range(1, 1024))
                .generate(field("team"), gen -> gen.oneOf(records))
                .set(field("name"), nameFaker.fullName())
                .supply(field("backNumber"), DummyDataGenerator::backNumberGenerator)
                .create())
            .collect(Collectors.toList());
    }

    public static List<Player> getPlayersByTeam(Integer size, Integer teamId) {
        var team = getTeamRecord(teamId);
        var nameFaker = new Faker(Locale.JAPAN).name();

        return IntStream.range(1, size)
            .mapToObj(i -> Instancio.of(Player.class)
                .generate(field("id"), gen -> gen.ints().range(1, 1024))
                .set(field("team"), team)
                .set(field("name"), nameFaker.fullName())
                .supply(field("backNumber"), DummyDataGenerator::backNumberGenerator)
                .create())
            .collect(Collectors.toList());
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
            .assign(
                Assign.valueOf(Team::name)
                    .to(Team::urlPath)
                    .as((String teamName) -> teamName.toLowerCase().replace(" ", "-"))
            )
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
