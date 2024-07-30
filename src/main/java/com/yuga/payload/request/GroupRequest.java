package com.yuga.payload.request;

import com.yuga.service.dto.Contact;
import com.yuga.service.dto.Group;
import com.yuga.service.dto.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupRequest {
    private Group group;
    private Pagination pagination;
    private List<Group> groupList;
    private Contact contact;
}
