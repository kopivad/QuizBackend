package com.kopivad.testingsystem.repository;

import com.kopivad.testingsystem.domain.api.ApiClient;

public interface ApiClientRepository {
    ApiClient findByName(String name);
}
