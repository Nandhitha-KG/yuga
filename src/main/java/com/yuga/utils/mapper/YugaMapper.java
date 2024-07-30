package com.yuga.utils.mapper;

import com.yuga.persistence.entity.*;
import com.yuga.service.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface YugaMapper {

    Countries mapToCountries(CountriesEntity countriesEntity);

    Tags mapToTags(TagsEntity tagsEntity);
    Group mapToGroup(GroupEntity groupEntity);

    @Mapping(target = "active", ignore = true)
    GroupEntity mapToGroupEntity(Group group);

    ContactEntity mapToContactEntity(Contact contact);

    GroupContacts mapToGroupContacts(GroupContactsEntity groupContactsEntity);

    Contact mapToContact(ContactEntity updatedContact);
}
