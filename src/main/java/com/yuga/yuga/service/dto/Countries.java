package com.yuga.yuga.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Countries {
    private UUID uuid;
    private String countryName;
    private String prefix;
    private String code;
}
