package com.yuga.persistence.repository;

import com.yuga.persistence.entity.TagsEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface TagsRepository extends R2dbcRepository<TagsEntity, UUID> {
    Mono<TagsEntity> findByTagName(String tagName);

    @Query("UPDATE tags SET is_selected = false WHERE is_selected = true")
    Mono<Void> resetAllTagsSelection();

    @Query("UPDATE tags SET is_selected = true WHERE uuid = :uuid")
    Mono<Void> selectTagById(UUID uuid);
}
