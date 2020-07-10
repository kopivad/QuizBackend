package com.kopivad.quizzes.mapper;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper mapper;

    public User toEntity(UserDto dto) {
        return mapper.map(dto, User.UserBuilder.class).build();
    }

    public UserDto toDto(User user) {
        return mapper.map(user, UserDto.UserDtoBuilder.class).build();
    }
}
