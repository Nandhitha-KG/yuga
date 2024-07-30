package com.yuga.yuga.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GroupContacts {
    private UUID uuid;
    private UUID contactId;
    private UUID groupId;
    private String groupName;
}
