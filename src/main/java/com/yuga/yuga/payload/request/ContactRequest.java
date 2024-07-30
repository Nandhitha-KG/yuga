package com.yuga.yuga.payload.request;

import com.yuga.yuga.service.dto.GroupContacts;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContactRequest {
    private String name;
    private String countryCode;
    private String mobile;
    private String email;
    private String address;
    private String tag;
    private List<GroupContacts> groupContacts;
}
