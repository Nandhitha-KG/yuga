package com.yuga.yuga.utils.mapper;

import com.yuga.yuga.persistence.entity.*;
import com.yuga.yuga.service.dto.*;
import com.yuga.yuga.service.dto.Countries;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface YugaMapper {

    Countries mapToCountries(CountriesEntity countriesEntity);

    Tags mapToTags(TagsEntity tagsEntity);

    Group mapToGroup(GroupEntity groupEntity);

    Contact mapToContact(ContactEntity contactEntity);

    @Mapping(target = "active", ignore = true)
    GroupEntity mapToGroupEntity(Group group);

    GroupContacts mapToGroupContacts(GroupContactsEntity groupContactsEntity);
}
