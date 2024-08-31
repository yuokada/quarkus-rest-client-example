package io.github.yuokada.rest.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;

//   {
//    "id": 1,
//    "date": "2014-03-01",
//    "start_time": "18:00",
//    "game_status": 2,
//    "top_status": 1,
//    "bottom_status": 1,
//    "stadium": "天王洲"
//  },
public record Game(
    Integer id,
    LocalDate date,
    @JsonProperty("start_time") LocalTime startTime,
    @JsonProperty("game_status") Integer gameStatus,
    @JsonProperty("top_status") Integer topStatus,
    @JsonProperty("bottom_status") Integer bottomStatus,
    @JsonProperty("stadium") String stadium
) {
}
