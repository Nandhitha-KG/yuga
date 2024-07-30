package com.yuga.yuga.service.impl;

import com.yuga.yuga.exception.ResourceNotFoundException;
import com.yuga.yuga.payload.request.ContactRequest;
import com.yuga.yuga.payload.request.TagsRequest;
import com.yuga.yuga.payload.response.*;
import com.yuga.yuga.persistence.entity.GroupContactsEntity;
import com.yuga.yuga.persistence.entity.TagsEntity;
import com.yuga.yuga.persistence.repository.*;
import com.yuga.yuga.service.YugaService;
import com.yuga.yuga.service.dto.Contact;
import com.yuga.yuga.service.dto.GroupContacts;
import com.yuga.yuga.service.dto.Tags;

import com.yuga.yuga.exception.BadRequestException;
import com.yuga.yuga.payload.request.GroupRequest;
import com.yuga.yuga.payload.response.ApiResponse;
import com.yuga.yuga.payload.response.Response;
import com.yuga.yuga.persistence.entity.GroupEntity;
import com.yuga.yuga.persistence.repository.CountriesRepository;
import com.yuga.yuga.service.YugaService;
import com.yuga.yuga.service.dto.Group;
import com.yuga.yuga.util.MessageKeyConstants;
import com.yuga.yuga.utils.mapper.YugaMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
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
    public Mono<ContactResponse> getContactDetails(UUID contactId) {
        return contactRepository.findById(contactId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(MessageKeyConstants.NO_DATA_FOUND)))
                .flatMap(contactEntity ->
                        groupContactsRepository.findByContactId(contactEntity.getUuid())
                                .collectList()
                                .map(groupContactsEntities -> {
                                    Contact contact = yugaMapper.mapToContact(contactEntity);
                                    List<GroupContacts> groupContacts = groupContactsEntities.stream()
                                            .map(yugaMapper::mapToGroupContacts)
                                            .collect(Collectors.toList());
                                    contact.setGroupContacts(groupContacts);
                                    ContactResponse contactResponse = new ContactResponse();
                                    contactResponse.setContactList(Collections.singletonList(contact));
                                    return contactResponse;
                                })
                );
    }

    @Override
    public Mono<ContactResponse> updateContact(UUID contactId, ContactRequest contactRequest) {
        return contactRepository.findById(contactId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(MessageKeyConstants.NO_DATA_FOUND)))
                .flatMap(existingContact -> {
                    if (contactRequest.getName() != null) {
                        existingContact.setName(contactRequest.getName());
                    }
                    if (contactRequest.getCountryCode() != null) {
                        existingContact.setCountryCode(contactRequest.getCountryCode());
                    }
                    if (contactRequest.getMobile() != null) {
                        existingContact.setMobile(contactRequest.getMobile());
                    }
                    if (contactRequest.getEmail() != null) {
                        existingContact.setEmail(contactRequest.getEmail());
                    }
                    if (contactRequest.getAddress() != null) {
                        existingContact.setAddress(contactRequest.getAddress());
                    }
                    if (contactRequest.getTag() != null) {
                        existingContact.setTag(contactRequest.getTag());
                    }

                    return contactRepository.save(existingContact)
                            .flatMap(updatedContact -> {
                                if (contactRequest.getGroupContacts() != null) {
                                    return groupContactsRepository.deleteByContactId(contactId)
                                            .thenMany(Flux.fromIterable(contactRequest.getGroupContacts())
                                                    .map(groupContactsRequest -> {
                                                        GroupContactsEntity groupContactsEntity = new GroupContactsEntity();
                                                        groupContactsEntity.setContactId(updatedContact.getUuid());
                                                        groupContactsEntity.setGroupId(groupContactsRequest.getGroupId());
                                                        groupContactsEntity.setGroupName(groupContactsRequest.getGroupName());
                                                        return groupContactsEntity;
                                                    })
                                                    .flatMap(groupContactsRepository::save))
                                            .then(Mono.just(updatedContact));
                                } else {
                                    return Mono.just(updatedContact);
                                }
                            })
                            .flatMap(updatedContact -> groupContactsRepository.findByContactId(updatedContact.getUuid())
                                    .collectList()
                                    .map(groupContactsEntities -> {
                                        Contact contact = yugaMapper.mapToContact(updatedContact);
                                        List<GroupContacts> groupContacts = groupContactsEntities.stream()
                                                .map(yugaMapper::mapToGroupContacts)
                                                .collect(Collectors.toList());
                                        contact.setGroupContacts(groupContacts);
                                        ContactResponse contactResponse = new ContactResponse();
                                        contactResponse.setContactList(Collections.singletonList(contact));
                                        return contactResponse;
                                    }));
                });
    }

}
