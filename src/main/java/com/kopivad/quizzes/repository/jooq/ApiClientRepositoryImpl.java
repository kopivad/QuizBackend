package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.api.ApiClient;
import com.kopivad.quizzes.repository.ApiClientRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

import static com.kopivad.quizzes.domain.db.tables.ApiClients.API_CLIENTS;

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

    private RecordMapper<Record, ApiClient> getApiClientFromRecord() {
        return record -> ApiClient
                .builder()
                .id(record.getValue(API_CLIENTS.ID))
                .username(record.getValue(API_CLIENTS.USERNAME))
                .password(record.getValue(API_CLIENTS.PASSWORD))
                .build();
    }
}
