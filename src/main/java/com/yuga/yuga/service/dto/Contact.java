package com.yuga.yuga.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Contact {
    private UUID uuid;
    private String name;
    private String countryCode;
    private String mobile;
    private String email;
    private String address;
    private String tag;
    private List<GroupContacts> groupContacts;
}
