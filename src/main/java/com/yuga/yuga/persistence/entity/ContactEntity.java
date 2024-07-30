package com.yuga.yuga.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Table(name = "contact")
public class ContactEntity {
    @Id
    private UUID uuid;
    private String name;
    private String countryCode;
    private String mobile;
    private String email;
    private String address;
    private String tag;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant modifiedAt;
}
