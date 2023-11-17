package io.github.yuokada.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;

public class Game {

    //   {
    //    "id": 1,
    //    "date": "2014-03-01",
    //    "start_time": "18:00",
    //    "game_status": 2,
    //    "top_status": 1,
    //    "bottom_status": 1,
    //    "stadium": "天王洲"
    //  },
    public Integer id;
    public LocalDate date;

    @JsonProperty("start_time")
    public LocalTime startTime;

    @JsonProperty("game_status")
    public Integer gameStatus;

    @JsonProperty("top_status")
    public Integer topStatus;

    @JsonProperty("bottom_status")
    public Integer bottomStatus;

    @JsonProperty("stadium")
    public String stadium;

    @Override
    public String toString() {
        return "Game{"
                + "id="
                + id
                + ", date="
                + date
                + ", startTime="
                + startTime
                + ", gameStatus="
                + gameStatus
                + ", topStatus="
                + topStatus
                + ", bottomStatus="
                + bottomStatus
                + ", stadium='"
                + stadium
                + '\''
                + '}';
    }
}
