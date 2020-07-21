package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.Role;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.UserDto;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;

public class UserUtils {
    public static List<User> generateUsers(int size) {
        return IntStream.range(INTEGER_ZERO, size)
                .mapToObj(i -> generateUser()
                        .toBuilder()
                        .id(i + LONG_ONE)
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }

    public static User generateUser() {
        Fairy fairy = Fairy.create();
        Person person = fairy.person();
        return User
                .builder()
                .id(LONG_ONE)
                .email(person.getEmail())
                .name(person.getUsername())
                .password(person.getPassword())
                .creationDate(LocalDateTime.now())
                .role(Role.USER)
                .build();
    }

    public static UserDto generateUserDto() {
        Fairy fairy = Fairy.create();
        Person person = fairy.person();
        return UserDto
                .builder()
                .id(LONG_ONE)
                .email(person.getEmail())
                .name(person.getUsername())
                .password(person.getPassword())
                .creationDate(LocalDateTime.now())
                .role(Role.USER)
                .build();
    }
}
