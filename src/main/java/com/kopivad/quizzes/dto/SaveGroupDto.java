package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class SaveGroupDto {
    String name;
    List<Long> usersIds;
    List<Long> quizzesIds;

    @JsonCreator
    public SaveGroupDto(
            @JsonProperty("name") String name,
            @JsonProperty("usersIds") List<Long> usersIds,
            @JsonProperty("quizzesIds") List<Long> quizzesIds
    ) {
        this.name = name;
        this.usersIds = usersIds;
        this.quizzesIds = quizzesIds;
    }
}
