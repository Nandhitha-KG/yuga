package com.yuga.yuga.utils.mapper;

import com.yuga.yuga.persistence.entity.CountriesEntity;
import com.yuga.yuga.persistence.entity.GroupEntity;
import com.yuga.yuga.service.dto.Countries;
import com.yuga.yuga.service.dto.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface YugaMapper {

    Countries mapToCountries(CountriesEntity countriesEntity);

    Group mapToGroup(GroupEntity groupEntity);

    @Mapping(target = "active", ignore = true)
    GroupEntity mapToGroupEntity(Group group);
}
