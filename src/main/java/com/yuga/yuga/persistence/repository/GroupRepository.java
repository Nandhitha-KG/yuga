package com.yuga.yuga.persistence.repository;

import com.yuga.yuga.persistence.entity.GroupEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface GroupRepository extends R2dbcRepository<GroupEntity, UUID> {

    @Query("UPDATE groups SET is_active = false WHERE uuid = :groupId")
    Mono<Void> markGroupAsInactive(@Param("groupId") UUID groupId);

    @Query("SELECT * FROM groups WHERE is_active = true LIMIT :#{#pageable.pageSize} OFFSET :#{#pageable.offset}")
    Flux<GroupEntity> findAllGroupsAndActiveTrue(Pageable pageable);

    @Query("SELECT COUNT(*) FROM groups WHERE is_active = true")
    Mono<Long> countActiveGroups();
}
