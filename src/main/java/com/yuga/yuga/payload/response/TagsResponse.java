package com.yuga.yuga.payload.response;

import com.yuga.yuga.service.dto.Tags;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TagsResponse extends ApiResponse{
    private List<Tags> tagsList;
}
