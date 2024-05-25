package io.github.yuokada.rest.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public record Team(
    Integer id,
    String name,
    @JsonProperty("url_path")
    @Schema(maxLength = 32)
    String urlPath,
    @JsonProperty("at_bats")
    // @JsonProperty("regulation_at_bats")
    @Schema(
        minimum = "0",
        maximum = "5"
    )
    Double regulationAtBats
) {

}
