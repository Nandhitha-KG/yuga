package com.yuga.yuga.persistence.repository;

import com.yuga.yuga.persistence.entity.GroupContactsEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface GroupContactsRepository extends R2dbcRepository<GroupContactsEntity, UUID> {
    Flux<GroupContactsEntity> findByContactId(UUID uuid);

    Mono<Void> deleteByContactId(UUID contactId);
}
