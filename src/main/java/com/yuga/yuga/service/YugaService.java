package com.yuga.yuga.service;

import com.yuga.yuga.payload.request.GroupRequest;
import com.yuga.yuga.payload.response.ApiResponse;
import com.yuga.yuga.payload.response.Response;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface YugaService {
    Mono<Response> getCountriesList();

    Mono<ApiResponse> createGroup(GroupRequest groupRequest);

    Mono<ApiResponse> deleteGroup(UUID groupId);

    Mono<Response> listOfGroup(GroupRequest groupRequest);
}
