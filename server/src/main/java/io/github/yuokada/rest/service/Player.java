package io.github.yuokada.rest.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {

    public Integer id;
    public String name;

    public Team team;

    @JsonProperty("backNumber")
    public String backNumber;

    @Override
    public String toString() {
        return "Player{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", team=" + team +
            ", backNumber='" + backNumber + '\'' +
            '}';
    }
}
