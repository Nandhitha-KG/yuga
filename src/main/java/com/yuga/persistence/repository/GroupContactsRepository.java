package com.yuga.persistence.repository;

import com.yuga.persistence.entity.GroupContactsEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupContactsRepository extends R2dbcRepository<GroupContactsEntity, UUID> {
}
