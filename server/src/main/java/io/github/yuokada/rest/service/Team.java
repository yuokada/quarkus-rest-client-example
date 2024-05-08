package io.github.yuokada.rest.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Team(
    Integer id,
    String name,
    @JsonProperty("url_path")
    String urlPath,
    @JsonProperty("at_bats")
    // @JsonProperty("regulation_at_bats")
    Double regulationAtBats
) {

}
