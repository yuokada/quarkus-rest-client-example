package io.github.yuokada.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Team {

    public Integer id;
    public String name;

    @JsonProperty("url_path")
    public String urlPath;

    @JsonProperty("regulation_at_bats")
    public Double regulationAtBats;

    @Override
    public String toString() {
        return "Team{"
                + "id="
                + id
                + ", name='"
                + name
                + '\''
                + ", urlPath='"
                + urlPath
                + '\''
                + ", regulationAtBats="
                + regulationAtBats
                + '}';
    }
}
