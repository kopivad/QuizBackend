package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.domain.api.ApiClient;
import com.kopivad.testingsystem.repository.ApiClientRepository;
import com.kopivad.testingsystem.service.ApiClientService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApiUserServiceImpl implements ApiClientService {
    private final ApiClientRepository clientRepository;
    @Override
    public ApiClient getByName(String name) {
        return clientRepository.findByName(name);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return getByName(s);
    }
}
