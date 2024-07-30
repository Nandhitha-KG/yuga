package com.yuga.yuga.payload.response;

import com.yuga.yuga.service.dto.Countries;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Response extends ApiResponse{
    private List<Countries> countriesList;
}
