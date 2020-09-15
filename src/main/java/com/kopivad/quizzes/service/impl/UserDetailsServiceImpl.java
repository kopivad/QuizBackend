package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(resultUser ->
                org.springframework.security.core.userdetails.User
                .builder()
                .username(resultUser.getEmail())
                .password(resultUser.getPassword())
                .roles(resultUser.getRole().name())
                .build()
        ).orElseThrow(() -> new UsernameNotFoundException(String.format("User with %s not found!", email)));
    }
}
