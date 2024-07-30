package com.yuga.yuga.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Table(name = "group_contacts")
public class GroupContactsEntity {
    @Id
    private UUID uuid;
    private UUID contactId;
    private UUID groupId;
    private UUID groupName;
    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant modifiedAt;
}
