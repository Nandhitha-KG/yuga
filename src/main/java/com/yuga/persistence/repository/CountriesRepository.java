package com.yuga.persistence.repository;

import com.yuga.persistence.entity.CountriesEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface CountriesRepository extends R2dbcRepository<CountriesEntity, UUID> {
    @Query("SELECT * FROM countries")
    Flux<CountriesEntity> findAllCountries();
}
