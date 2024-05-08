package io.github.yuokada.rest.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Player(
    Integer id,
    String name,
    Team team,
    @JsonProperty("backNumber")
    String backNumber
) {

}
