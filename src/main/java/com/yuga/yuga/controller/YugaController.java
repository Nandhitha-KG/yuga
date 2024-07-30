package com.yuga.yuga.controller;

import com.yuga.yuga.payload.request.GroupRequest;
import com.yuga.yuga.payload.request.TagsRequest;
import com.yuga.yuga.payload.response.ApiResponse;
import com.yuga.yuga.payload.response.Response;
import com.yuga.yuga.payload.response.TagsResponse;
import com.yuga.yuga.service.YugaService;
import com.yuga.yuga.util.CommonUtils;
import com.yuga.yuga.util.MessageKeyConstants;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/yuga")
public class YugaController {

    private final YugaService yugaService;

    private final MessageSource messageSource;

    public YugaController(YugaService yugaService, MessageSource messageSource) {
        this.yugaService = yugaService;
        this.messageSource = messageSource;
    }

    @GetMapping("/countries")
    public Mono<ResponseEntity<Response>> getCountriesList() {
        return yugaService.getCountriesList().flatMap(response -> {
            Response response1 = CommonUtils
                    .buildSuccessResponse(MessageKeyConstants.SUCCESS, messageSource, HttpStatus.OK, response);
            return Mono.just(ResponseEntity.status(HttpStatus.OK).body(response1));
        });
    }
    @PostMapping("/tags")
    public Mono<ResponseEntity<ApiResponse>> addTags(@RequestBody TagsRequest tagsRequest) {
        return yugaService.addTags(tagsRequest).flatMap(response -> {
            ApiResponse apiResponse = CommonUtils.buildSuccessResponse(MessageKeyConstants.SUCCESS, messageSource,
                    HttpStatus.OK, response);
            return Mono.just(ResponseEntity.status(HttpStatus.OK).body(apiResponse));
        });
    }

    @PostMapping("/group")
    public Mono<ResponseEntity<ApiResponse>> createGroup(@RequestBody GroupRequest groupRequest) {
        return yugaService.createGroup(groupRequest).flatMap(response -> {
            ApiResponse apiResponse = CommonUtils.buildSuccessResponse(MessageKeyConstants.SUCCESS, messageSource,
                    HttpStatus.OK, response);
            return Mono.just(ResponseEntity.status(HttpStatus.OK).body(apiResponse));
        });
    }

    @GetMapping("/tagsList")
    public Mono<ResponseEntity<TagsResponse>> getTagsList() {
        return yugaService.getTagsList().flatMap(response -> {
            TagsResponse tagsResponse = CommonUtils
                    .buildSuccessResponse(MessageKeyConstants.SUCCESS, messageSource, HttpStatus.OK, response);
            return Mono.just(ResponseEntity.status(HttpStatus.OK).body(tagsResponse));
        });
    }

    @PutMapping("/tags/select/{uuid}")
    public Mono<ResponseEntity<ApiResponse>> selectTag(@PathVariable UUID uuid) {
        return yugaService.selectTag(uuid).flatMap(response -> {
            ApiResponse apiResponse = CommonUtils
                    .buildSuccessResponse(MessageKeyConstants.SUCCESS, messageSource, HttpStatus.OK, response);
            return Mono.just(ResponseEntity.status(HttpStatus.OK).body(apiResponse));
        });
    }

    @DeleteMapping("/group/{groupId}")
    public Mono<ResponseEntity<ApiResponse>> deleteGroup(@PathVariable UUID groupId) {
        return yugaService.deleteGroup(groupId).flatMap(response -> {
            ApiResponse apiResponse = CommonUtils.buildSuccessResponse(MessageKeyConstants.GROUP_DELETED_SUCCESS, messageSource,
                    HttpStatus.OK, response);
            return Mono.just(ResponseEntity.status(HttpStatus.OK).body(apiResponse));
        });
    }

    @PostMapping("/group/list")
    public Mono<ResponseEntity<Response>> listOfGroup(@RequestBody GroupRequest groupRequest) {
        return yugaService.listOfGroup(groupRequest).flatMap(response -> {
            Response response1 = CommonUtils.buildSuccessResponse(MessageKeyConstants.SUCCESS, messageSource,
                    HttpStatus.OK, response);
            return Mono.just(ResponseEntity.status(HttpStatus.OK).body(response1));
        });
    }

    @PutMapping("/group")
    public Mono<ResponseEntity<ApiResponse>> updateGroup(@RequestBody GroupRequest groupRequest) {
        return yugaService.updateGroup(groupRequest).flatMap(response -> {
            ApiResponse response1 = CommonUtils.buildSuccessResponse(MessageKeyConstants.SUCCESS, messageSource,
                    HttpStatus.OK, response);
            return Mono.just(ResponseEntity.status(HttpStatus.OK).body(response1));
        });
    }

    @PutMapping("/group/select")
    public Mono<ResponseEntity<ApiResponse>> selectGroup(@RequestBody GroupRequest groupRequest) {
        return yugaService.selectGroup(groupRequest).flatMap(response -> {
            ApiResponse response1 = CommonUtils.buildSuccessResponse(MessageKeyConstants.SUCCESS, messageSource,
                    HttpStatus.OK, response);
            return Mono.just(ResponseEntity.status(HttpStatus.OK).body(response1));
        });
    }
}
