package com.yuga.yuga.utils.mapper;

import com.yuga.yuga.persistence.entity.CountriesEntity;
import com.yuga.yuga.persistence.entity.TagsEntity;
import com.yuga.yuga.service.dto.Countries;
import com.yuga.yuga.service.dto.Tags;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface YugaMapper {

    Countries mapToCountries(CountriesEntity countriesEntity);

    Tags mapToTags(TagsEntity tagsEntity);
}
