package com.yuga.yuga.service.impl;


import com.yuga.yuga.payload.response.Response;
import com.yuga.yuga.persistence.repository.CountriesRepository;
import com.yuga.yuga.service.YugaService;
import com.yuga.yuga.service.dto.Countries;
import com.yuga.yuga.utils.mapper.YugaMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class YugaServiceImpl implements YugaService{

    private final CountriesRepository countriesRepository;

    private final YugaMapper yugaMapper;

    public YugaServiceImpl(CountriesRepository countriesRepository, YugaMapper yugaMapper) {
        this.countriesRepository = countriesRepository;
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

}
