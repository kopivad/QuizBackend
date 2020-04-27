package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.repository.UserRepository;
import com.kopivad.quizzes.service.QuizService;
import com.kopivad.quizzes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Qualifier("jooqUserRepository")
    private final UserRepository userRepository;
    private final QuizService quizService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        User userFromDB = userRepository.findById(id);
        List<Quiz> userQuizzes = quizService.getAll()
                .stream()
                .filter(q -> q.getAuthor().getId().equals(id))
                .collect(Collectors.toList());
        userFromDB.setQuizzes(userQuizzes);
        return userFromDB;
    }

    @Override
    public User save(User user) {
        String userPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(userPassword));
        user.setCreationDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        String userPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(userPassword));
        return userRepository.update(id, user);
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }
}
