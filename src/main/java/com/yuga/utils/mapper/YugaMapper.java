package com.yuga.utils.mapper;

import com.yuga.persistence.entity.ContactEntity;
import com.yuga.persistence.entity.CountriesEntity;
import com.yuga.persistence.entity.GroupEntity;
import com.yuga.persistence.entity.TagsEntity;
import com.yuga.service.dto.Contact;
import com.yuga.service.dto.Countries;
import com.yuga.service.dto.Group;
import com.yuga.service.dto.Tags;
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
}
