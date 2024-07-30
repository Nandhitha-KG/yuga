package com.yuga.service.impl;

import com.yuga.exception.BadRequestException;
import com.yuga.exception.ResourceNotFoundException;
import com.yuga.payload.request.TagsRequest;
import com.yuga.payload.response.TagsResponse;
import com.yuga.persistence.entity.ContactEntity;
import com.yuga.persistence.entity.GroupContactsEntity;
import com.yuga.persistence.entity.GroupEntity;
import com.yuga.persistence.entity.TagsEntity;
import com.yuga.persistence.repository.*;
import com.yuga.service.dto.Contact;
import com.yuga.service.dto.Tags;
import com.yuga.payload.response.ApiResponse;
import com.yuga.payload.response.Response;
import com.yuga.service.YugaService;

import com.yuga.payload.request.GroupRequest;
import com.yuga.payload.response.PageDetails;
import com.yuga.service.dto.Group;
import com.yuga.util.MessageKeyConstants;
import com.yuga.utils.mapper.YugaMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class YugaServiceImpl implements YugaService{

    private final CountriesRepository countriesRepository;
    private final GroupRepository groupRepository;

    private final TagsRepository tagsRepository;

    private final ContactRepository contactRepository;

    private final GroupContactsRepository groupContactsRepository;

    private final YugaMapper yugaMapper;


    public YugaServiceImpl(CountriesRepository countriesRepository, GroupRepository groupRepository, TagsRepository tagsRepository, ContactRepository contactRepository, GroupContactsRepository groupContactsRepository, YugaMapper yugaMapper) {
        this.countriesRepository = countriesRepository;
        this.groupRepository = groupRepository;
        this.tagsRepository = tagsRepository;
        this.contactRepository = contactRepository;
        this.groupContactsRepository = groupContactsRepository;
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
    public Mono<ApiResponse> createGroup(GroupRequest groupRequest) {
        GroupEntity groupEntity = yugaMapper.mapToGroupEntity(groupRequest.getGroup());
        groupEntity.setActive(true);
        return groupRepository.findByGroupName(groupRequest.getGroup().getGroupName())
                .flatMap(existingGroup -> Mono.error(new BadRequestException(MessageKeyConstants.GROUP_ALREADY_EXISt)))
                .switchIfEmpty(groupRepository.save(groupEntity))
                .map(groupEntity1->new ApiResponse());
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

    public Mono<ApiResponse> updateGroup(GroupRequest GroupRequest) {
        return groupRepository.updateGroup(
                        GroupRequest.getGroup().getUuid(),
                        GroupRequest.getGroup().getGroupName(),
                        GroupRequest.getGroup().getDescription()
                )
                .then(Mono.just(new ApiResponse()))
                .onErrorResume(e -> Mono.error(new BadRequestException(MessageKeyConstants.FAILED_TO_UPDATE)));
    }

    @Override
    public Mono<ApiResponse> selectGroup(GroupRequest groupRequest) {
        List<Group> groups = groupRequest.getGroupList();
        return Flux.fromIterable(groups)
                .flatMap(group -> groupRepository.findById(group.getUuid())
                        .flatMap(groupEntity -> {
                            groupEntity.setContactSelected(true);
                            return groupRepository.save(groupEntity);
                        })
                        .onErrorResume(e -> Mono.empty()))
                .then(Mono.just(new ApiResponse()))
                .onErrorResume(e -> Mono.error(new BadRequestException(MessageKeyConstants.FAILED_TO_UPDATE)));
    }

    @Override
    public Mono<ApiResponse> addContact(GroupRequest groupRequest) {
        Contact contactDto = groupRequest.getContact();
        return tagsRepository.findByTagName(contactDto.getTag())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(MessageKeyConstants.TAG_NOT_FOUND)))
                .flatMap(tagEntity -> {

                    return contactRepository.findByMobileAndEmail(contactDto.getMobile(), contactDto.getEmail())
                            .flatMap(existingContact -> Mono.error(new BadRequestException(MessageKeyConstants.CONTACT_ALREADY_EXIST)))
                            .switchIfEmpty(Mono.just(contactDto))
                            .flatMap(contact ->
                                    Flux.fromIterable(contactDto.getGroupContacts())
                                            .flatMap(gcDto -> groupRepository.findById(gcDto.getGroupId())
                                                    .map(groupEntity -> {
                                                        gcDto.setGroupName(groupEntity.getGroupName());
                                                        return gcDto;
                                                    })
                                                    .switchIfEmpty(Mono.error(new ResourceNotFoundException(MessageKeyConstants.GROUP_NOT_FOUND)))
                                            )
                                            .collectList()
                                            .flatMap(groupContacts -> {
                                                ContactEntity contactEntity = yugaMapper.mapToContactEntity(contactDto);
                                                return contactRepository.save(contactEntity)
                                                        .flatMap(savedContact -> {
                                                            var groupContactsEntities = groupContacts.stream()
                                                                    .map(gcDto -> {
                                                                        GroupContactsEntity gcEntity = new GroupContactsEntity();
                                                                        gcEntity.setContactId(savedContact.getUuid());
                                                                        gcEntity.setGroupId(gcDto.getGroupId());
                                                                        gcEntity.setGroupName(gcDto.getGroupName());
                                                                        return gcEntity;
                                                                    }).toList();
                                                            return groupContactsRepository.saveAll(groupContactsEntities)
                                                                    .collectList()
                                                                    .map(savedGroupContacts -> new ApiResponse());
                                                        });
                                            })
                            );
                });
    }
}
