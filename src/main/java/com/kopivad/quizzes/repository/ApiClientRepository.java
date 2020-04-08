package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.api.ApiClient;

public interface ApiClientRepository {
    ApiClient findByUsername(String name);
}
