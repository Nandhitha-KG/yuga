package com.yuga.yuga.service.impl;


import com.yuga.yuga.payload.request.GroupRequest;
import com.yuga.yuga.payload.response.ApiResponse;
import com.yuga.yuga.payload.response.PageDetails;
import com.yuga.yuga.payload.response.Response;
import com.yuga.yuga.persistence.entity.GroupEntity;
import com.yuga.yuga.persistence.repository.CountriesRepository;
import com.yuga.yuga.persistence.repository.GroupRepository;
import com.yuga.yuga.service.YugaService;
import com.yuga.yuga.service.dto.Group;
import com.yuga.yuga.utils.mapper.YugaMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
public class YugaServiceImpl implements YugaService{

    private final CountriesRepository countriesRepository;
    private final GroupRepository groupRepository;

    private final YugaMapper yugaMapper;

    public YugaServiceImpl(CountriesRepository countriesRepository, GroupRepository groupRepository, YugaMapper yugaMapper) {
        this.countriesRepository = countriesRepository;
        this.groupRepository = groupRepository;
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
    public Mono<ApiResponse> createGroup(GroupRequest groupRequest) {
        GroupEntity groupEntity = yugaMapper.mapToGroupEntity(groupRequest.getGroup());
        groupEntity.setActive(true);
        return groupRepository.save(groupEntity)
                .map(savedGroup -> new ApiResponse());
    }

    public Mono<ApiResponse> deleteGroup(UUID groupId) {
        return groupRepository.markGroupAsInactive(groupId)
                .then(Mono.just(new ApiResponse()));
    }

    @Override
    public Mono<Response> listOfGroup(GroupRequest groupRequest) {
        PageRequest pageable = PageRequest.of(groupRequest.getPagination().getPage(), groupRequest.getPagination().getSize());
        Mono<Long> countMono = groupRepository.countActiveGroups();
        Mono<List<Group>> groupsMono = groupRepository.findAllGroupsAndActiveTrue(pageable)
                .map(yugaMapper::mapToGroup)
                .collectList();
        return Mono.zip(countMono, groupsMono)
                .map(tuple -> {
                    long count = tuple.getT1();
                    List<Group> groups = tuple.getT2();
                    Response response = new Response();
                    response.setGroups(groups);
                    PageDetails pageDetails=new PageDetails();
                    pageDetails.setTotalCount(count);
                    pageDetails.setPage(groupRequest.getPagination().getPage());
                    pageDetails.setSize(groupRequest.getPagination().getSize());
                    response.setPageDetails(pageDetails);
                    return response;
                });
    }

}
