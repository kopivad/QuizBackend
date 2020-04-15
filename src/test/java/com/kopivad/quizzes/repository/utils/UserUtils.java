package com.kopivad.quizzes.repository.utils;

import com.kopivad.quizzes.domain.User;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UserUtils {
    public static List<User> generateUsers(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> generateUser())
                .collect(Collectors.toList());
    }

    public static User generateUser() {
        Fairy fairy = Fairy.create();
        Person person = fairy.person();
        return User
                .builder()
                .email(person.getEmail())
                .name(person.getFirstName())
                .password(person.getPassword())
                .creationDate(LocalDateTime.now())
                .role("USER")
                .build();
    }
}
