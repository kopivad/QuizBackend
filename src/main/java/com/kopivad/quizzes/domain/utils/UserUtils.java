package com.kopivad.quizzes.domain.utils;

import com.kopivad.quizzes.domain.User;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;

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
                .role("USER")
                .build();
    }
}
