package com.yuga.service;

import com.yuga.payload.request.ContactRequest;
import com.yuga.payload.response.ContactResponse;
import com.yuga.payload.request.TagsRequest;
import com.yuga.payload.response.TagsResponse;
import com.yuga.payload.response.ApiResponse;
import com.yuga.payload.response.Response;
import com.yuga.payload.request.GroupRequest;
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

    Mono<ContactResponse> getContactDetails(UUID contactId);

    Mono<ContactResponse> updateContact(UUID contactId, ContactRequest contactRequest);

    Mono<ApiResponse> selectGroup(GroupRequest groupRequest);

    Mono<ApiResponse> addContact(GroupRequest groupRequest);
}
