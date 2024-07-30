package com.yuga.persistence.repository;

import com.yuga.persistence.entity.ContactEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ContactRepository extends R2dbcRepository<ContactEntity, UUID> {
    Mono<ContactEntity> findByMobile(String mobile);
    Mono<ContactEntity> findByEmail(String email);
}
