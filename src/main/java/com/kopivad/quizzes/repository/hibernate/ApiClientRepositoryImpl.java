package com.kopivad.quizzes.repository.hibernate;

import com.kopivad.quizzes.domain.api.ApiClient;
import com.kopivad.quizzes.repository.ApiClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("hibernateApiClientRepository")
@RequiredArgsConstructor
public class ApiClientRepositoryImpl implements ApiClientRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public ApiClient findByUsername(String username) {
        return entityManager
                .createQuery("select c from ApiClient c where username = :username", ApiClient.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
