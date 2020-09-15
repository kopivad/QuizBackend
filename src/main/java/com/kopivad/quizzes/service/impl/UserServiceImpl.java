package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Role;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.RegisterUserDto;
import com.kopivad.quizzes.dto.SaveUserDto;
import com.kopivad.quizzes.repository.UserRepository;
import com.kopivad.quizzes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean save(SaveUserDto dto) {
        User user = new User(
                1L,
                dto.getName(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getRole(),
                LocalDateTime.now()
        );
        return userRepository.save(user);
    }

    @Override
    public boolean register(RegisterUserDto dto) {
        User user = new User(
                1L,
                dto.getName(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                Role.USER,
                LocalDateTime.now()
        );
        return userRepository.save(user);
    }

    @Override
    public boolean update(User user) {
        return userRepository.update(user);
    }

    @Override
    public boolean delete(Long id) {
        return userRepository.delete(id);
    }

    @Override
    public List<User> getByGroupId(long id) {
        return userRepository.findByGroupId(id);
    }

    @Override
    public List<User> getByEmailStartsWith(String email) {
        return userRepository.findByEmailStartsWith(email);
    }

    @Override
    public boolean updatePassword(long userId, String password) {
        return userRepository.updatePassword(userId, password);
    }
}
