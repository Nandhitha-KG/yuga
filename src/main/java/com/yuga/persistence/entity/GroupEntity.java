package com.yuga.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Table(name="groups")
public class GroupEntity {
    @Id
    private UUID uuid;
    private String groupName;
    private String description;
    private int noOfContact;
    private LocalDateTime lastContacted;
    private boolean isActive;
    private boolean isContactSelected;
    private boolean isGroupSelected;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String modifiedBy;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant modifiedAt;
}
