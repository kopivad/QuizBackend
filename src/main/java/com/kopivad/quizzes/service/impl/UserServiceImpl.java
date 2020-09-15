package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.UserDto;
import com.kopivad.quizzes.repository.UserRepository;
import com.kopivad.quizzes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;

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
    public boolean save(UserDto dto) {
        int affectedRows = userRepository.save(getEncryptedPasswordUserDto(dto));
        return affectedRows == INTEGER_ONE;
    }

    private UserDto getEncryptedPasswordUserDto(UserDto dto) {
        return new UserDto(
                dto.getEmail(),
                dto.getName(),
                passwordEncoder.encode(dto.getPassword())
        );
    }

    @Override
    public boolean update(User user) {
        int affectedRows = userRepository.update(user);
        return affectedRows == INTEGER_ONE;
    }

    @Override
    public boolean delete(Long id) {
        int affectedRows = userRepository.delete(id);
        return affectedRows == INTEGER_ONE;
    }

    @Override
    public List<User> getByEmailStartsWith(String email) {
        return userRepository.findByEmailStartsWith(email);
    }

    @Override
    public boolean updatePassword(long userId, String password) {
        int affectedRows = userRepository.updatePassword(userId, password);
        return affectedRows == INTEGER_ONE;
    }
}
