package com.yuga.yuga.service.impl;

import com.yuga.yuga.payload.request.TagsRequest;
import com.yuga.yuga.payload.response.ApiResponse;
import com.yuga.yuga.payload.response.Response;
import com.yuga.yuga.payload.response.TagsResponse;
import com.yuga.yuga.persistence.entity.TagsEntity;
import com.yuga.yuga.persistence.repository.CountriesRepository;
import com.yuga.yuga.persistence.repository.TagsRepository;
import com.yuga.yuga.service.YugaService;
import com.yuga.yuga.service.dto.Tags;
import com.yuga.yuga.utils.mapper.YugaMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class YugaServiceImpl implements YugaService{

    private final CountriesRepository countriesRepository;

    private final TagsRepository tagsRepository;

    private final YugaMapper yugaMapper;

    public YugaServiceImpl(CountriesRepository countriesRepository, TagsRepository tagsRepository, YugaMapper yugaMapper) {
        this.countriesRepository = countriesRepository;
        this.tagsRepository = tagsRepository;
        this.yugaMapper = yugaMapper;
    }

    @Override
    public Mono<Response> getCountriesList() {
        return countriesRepository.findAllCountries()
                .map(countriesEntity -> yugaMapper.mapToCountries(countriesEntity))
                .collectList()
                .map(countriesDto -> {
                    Response response = new Response();
                    response.setCountriesList(countriesDto);
                    return response;
                });
    }

    @Override
    public Mono<ApiResponse> addTags(TagsRequest tagsRequest) {
        return isTagExists(tagsRequest.getTags().getTagName())
                .switchIfEmpty(Mono.defer(() -> {
                    TagsEntity tag = new TagsEntity();
                    tag.setTagName(tagsRequest.getTags().getTagName());
                    return tagsRepository.save(tag);
                }))
                .map(savedTag -> new ApiResponse())
                .onErrorResume(e -> Mono.just(new ApiResponse()));
    }

    private Mono<TagsEntity> isTagExists(String tagName) {
        return tagsRepository.findByTagName(tagName);
    }

    @Override
    public Mono<TagsResponse> getTagsList() {
        return tagsRepository.findAll()
                .map(tagsEntity -> yugaMapper.mapToTags(tagsEntity))
                .collectList()
                .map(tags -> {
                    TagsResponse response = new TagsResponse();
                    response.setTagsList(tags);
                    return response;
                });
    }

    @Override
    public Mono<ApiResponse> selectTag(UUID uuid) {
        return tagsRepository.resetAllTagsSelection()
                .then(tagsRepository.selectTagById(uuid))
                .thenMany(tagsRepository.findAll())
                .collectList()
                .map(tagsEntities -> {
                    List<Tags> tagsList = tagsEntities.stream()
                            .map(yugaMapper::mapToTags)
                            .collect(Collectors.toList());
                    ApiResponse apiResponse = new ApiResponse();
                    return apiResponse;
                });
    }
}
