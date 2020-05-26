package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.form.UserForm;
import com.kopivad.quizzes.repository.UserRepository;
import com.kopivad.quizzes.service.QuizService;
import com.kopivad.quizzes.service.UserService;
import com.kopivad.quizzes.utils.FormUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
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
                .collect(Collectors.toUnmodifiableList());

        User userWithQuizzes = userFromDB
                .toBuilder()
                .quizzes(userQuizzes)
                .build();
        return userWithQuizzes;
    }

    @Override
    public User save(UserForm userForm) {
        User user = FormUtils.toUser(userForm);
        User userWithEncodedPassword = user
                .toBuilder()
                .password(passwordEncoder.encode(user.getPassword()))
                .creationDate(LocalDateTime.now())
                .build();
        return userRepository.save(userWithEncodedPassword);
    }

    @Override
    public User update(Long id, UserForm userForm) {
        User user = FormUtils.toUser(userForm);
        User userWithEncodedPassword = user
                .toBuilder()
                .password(passwordEncoder.encode(userForm.getPassword()))
                .build();
        return userRepository.update(id, userWithEncodedPassword);
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(id);
    }
}
