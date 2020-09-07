package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.kopivad.quizzes.domain.Role;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
@JsonDeserialize(builder = UserDto.UserDtoBuilder.class)
public class UserDto {
    long id;
    String email;
    String name;
    String password;
    Role role;
    LocalDateTime creationDate;
    List<QuizDto> quizzes;
    List<QuizHistoryDto> histories;
    List<QuizSessionDto> sessions;
    List<GroupDto> groups;
    String token;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserDtoBuilder { }
}
