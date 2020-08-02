package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

@Value
@EqualsAndHashCode(of = {"id"})
@Builder(toBuilder = true)
@JsonDeserialize(builder = GroupDto.GroupDtoBuilder.class)
public class GroupDto {
    long id;
    String name;
    List<QuizDto> quizzes;
    List<UserDto> users;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GroupDtoBuilder { }
}
