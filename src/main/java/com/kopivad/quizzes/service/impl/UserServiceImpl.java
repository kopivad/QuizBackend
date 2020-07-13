package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.UserDto;
import com.kopivad.quizzes.mapper.UserMapper;
import com.kopivad.quizzes.repository.UserRepository;
import com.kopivad.quizzes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public long save(UserDto userDto) {
        User user = mapper.toEntity(userDto);
        User userWithEncodedPassword = user
                .toBuilder()
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        return userRepository.save(userWithEncodedPassword);
    }

    @Override
    public boolean update(UserDto userDto) {
        User user = mapper.toEntity(userDto);
        User userWithEncodedPassword = user
                .toBuilder()
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();
        return userRepository.update(userWithEncodedPassword);
    }

    @Override
    public boolean delete(Long id) {
        return userRepository.delete(id);
    }
}
