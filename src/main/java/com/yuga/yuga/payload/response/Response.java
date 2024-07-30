package com.yuga.yuga.payload.response;

import com.yuga.yuga.service.dto.Countries;
import com.yuga.yuga.service.dto.Group;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Response extends ApiResponse{
    private List<Countries> countriesList;
    private List<Group> groups;
    private PageDetails pageDetails;
}
