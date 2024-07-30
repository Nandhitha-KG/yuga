package com.yuga.yuga.utils.mapper;

import com.yuga.yuga.persistence.entity.CountriesEntity;
import com.yuga.yuga.service.dto.Countries;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface YugaMapper {

    Countries mapToCountries(CountriesEntity countriesEntity);
}
