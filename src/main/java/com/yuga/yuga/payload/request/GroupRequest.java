package com.yuga.yuga.payload.request;

import com.yuga.yuga.service.dto.Group;
import com.yuga.yuga.service.dto.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupRequest {
    private Group group;
    private Pagination pagination;
    private List<Group> groupList;
}
