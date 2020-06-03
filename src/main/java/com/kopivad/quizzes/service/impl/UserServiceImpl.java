package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.form.UserForm;
import com.kopivad.quizzes.repository.UserRepository;
import com.kopivad.quizzes.service.UserService;
import com.kopivad.quizzes.utils.FormUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id);
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
