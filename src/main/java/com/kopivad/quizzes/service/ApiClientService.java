package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.api.ApiClient;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface ApiClientService extends UserDetailsService {
    ApiClient getByName(String name);
}
