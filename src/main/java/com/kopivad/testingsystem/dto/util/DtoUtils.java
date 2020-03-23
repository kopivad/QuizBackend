package com.kopivad.testingsystem.dto.util;

import com.kopivad.testingsystem.domain.User;
import com.kopivad.testingsystem.dto.UserDto;

public class DtoUtils {
    public static User getUserFromDto(UserDto dto) {
        return User.builder().name(dto.getName()).password(dto.getPassword()).email(dto.getEmail()).role(dto.getRole()).build();
    }
}
