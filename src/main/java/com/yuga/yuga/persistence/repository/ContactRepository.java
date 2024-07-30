package com.yuga.yuga.persistence.repository;

import com.yuga.yuga.persistence.entity.ContactEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContactRepository extends R2dbcRepository<ContactEntity, UUID> {
}
