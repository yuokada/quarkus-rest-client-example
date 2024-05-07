package io.github.yuokada.rest.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TeamRecord(
    Integer id,
    String name,
    String urlPath,
    @JsonProperty("at_bats")
    Double regulationAtBats
) {

}
