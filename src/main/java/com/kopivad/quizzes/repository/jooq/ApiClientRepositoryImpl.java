package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.api.ApiClient;
import com.kopivad.quizzes.repository.ApiClientRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static com.kopivad.quizzes.domain.db.tables.ApiClients.API_CLIENTS;
import static com.kopivad.quizzes.repository.jooq.RecordMappers.getApiClientFromRecord;

@Repository
@RequiredArgsConstructor
public class ApiClientRepositoryImpl implements ApiClientRepository {
    private final DSLContext dslContext;

    @Override
    public ApiClient findByUsername(String name) {
        return dslContext
                .selectFrom(API_CLIENTS)
                .where(API_CLIENTS.USERNAME.eq(name))
                .fetchOne()
                .map(getApiClientFromRecord());
    }
}
