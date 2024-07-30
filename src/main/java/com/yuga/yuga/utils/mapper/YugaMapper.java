package com.yuga.yuga.utils.mapper;

import com.yuga.yuga.persistence.entity.CountriesEntity;
import com.yuga.yuga.persistence.entity.TagsEntity;
import com.yuga.yuga.service.dto.Countries;
import com.yuga.yuga.service.dto.Tags;
import com.yuga.yuga.persistence.entity.GroupEntity;
import com.yuga.yuga.service.dto.Countries;
import com.yuga.yuga.service.dto.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface YugaMapper {

    Countries mapToCountries(CountriesEntity countriesEntity);

    Tags mapToTags(TagsEntity tagsEntity);
    Group mapToGroup(GroupEntity groupEntity);

    @Mapping(target = "active", ignore = true)
    GroupEntity mapToGroupEntity(Group group);
}
