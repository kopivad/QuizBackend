package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class GroupDto {
    String name;
    String joinCode;
    List<Long> usersIds;
    List<Long> quizzesIds;

    @JsonCreator
    public GroupDto(
            @JsonProperty("name") String name,
            @JsonProperty("joinCode") String joinCode,
            @JsonProperty("usersIds") List<Long> usersIds,
            @JsonProperty("quizzesIds") List<Long> quizzesIds
    ) {
        this.name = name;
        this.joinCode = joinCode;
        this.usersIds = usersIds;
        this.quizzesIds = quizzesIds;
    }
}
