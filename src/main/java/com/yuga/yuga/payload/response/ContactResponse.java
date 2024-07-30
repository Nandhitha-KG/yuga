package com.yuga.yuga.payload.response;

import com.yuga.yuga.service.dto.Contact;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContactResponse extends ApiResponse{
    private List<Contact> contactList;
}
