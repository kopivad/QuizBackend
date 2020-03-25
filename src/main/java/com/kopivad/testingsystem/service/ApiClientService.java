package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.domain.api.ApiClient;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface ApiClientService extends UserDetailsService {
    ApiClient getByName(String name);
}
