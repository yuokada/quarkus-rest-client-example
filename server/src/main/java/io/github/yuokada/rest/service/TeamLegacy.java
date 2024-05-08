package io.github.yuokada.rest.service;

import com.fasterxml.jackson.annotation.JsonProperty;

@Deprecated
public class TeamLegacy {

    public Integer id;
    public String name;

    @JsonProperty("url_path")
    public String urlPath;

    @JsonProperty("regulation_at_bats")
    public Double regulationAtBats;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrlPath() {
        return urlPath;
    }

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
