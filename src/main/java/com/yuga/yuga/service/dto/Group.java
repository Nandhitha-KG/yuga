package com.yuga.yuga.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Group {
    private UUID uuid;
    private String groupName;
    private String description;
    private int noOfContact;
    private LocalDateTime lastContacted;
    private boolean isActive;
    private boolean isContactSelected;
    private boolean isGroupSelected;
}
