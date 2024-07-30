package com.yuga.yuga.service;

import com.yuga.yuga.payload.response.Response;
import reactor.core.publisher.Mono;

public interface YugaService {
    Mono<Response> getCountriesList();
}
