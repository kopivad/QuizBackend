package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.api.ApiClient;
import com.kopivad.quizzes.repository.ApiClientRepository;
import com.kopivad.quizzes.service.ApiClientService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApiClientServiceImpl implements ApiClientService {
    @Qualifier("hibernateApiClientRepository")
    private final ApiClientRepository clientRepository;
    @Override
    public ApiClient getByName(String name) {
        return clientRepository.findByUsername(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getByName(username);
    }
}
