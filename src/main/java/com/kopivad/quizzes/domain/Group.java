package com.kopivad.quizzes.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class Group {
    long id;
    String name;
    String joinCode;

    @JsonCreator
    public Group(
            @JsonProperty("id") long id,
            @JsonProperty("name") String name,
            @JsonProperty("joinCode") String joinCode
    ) {
        this.id = id;
        this.name = name;
        this.joinCode = joinCode;
    }
}
