package com.yuga.yuga.service;

import com.yuga.yuga.payload.request.TagsRequest;
import com.yuga.yuga.payload.response.ApiResponse;
import com.yuga.yuga.payload.response.Response;
import com.yuga.yuga.payload.response.TagsResponse;
import com.yuga.yuga.payload.request.GroupRequest;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface YugaService {

    Mono<Response> getCountriesList();

    Mono<ApiResponse> addTags(TagsRequest tagsRequest);

    Mono<TagsResponse> getTagsList();

    Mono<ApiResponse> selectTag(UUID uuid);

    Mono<ApiResponse> createGroup(GroupRequest groupRequest);

    Mono<ApiResponse> deleteGroup(UUID groupId);

    Mono<Response> listOfGroup(GroupRequest groupRequest);

    Mono<ApiResponse> updateGroup(GroupRequest groupRequest);

    Mono<ApiResponse> selectGroup(GroupRequest groupRequest);
}
