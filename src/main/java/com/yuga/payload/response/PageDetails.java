package com.yuga.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageDetails {
    private long totalCount;
    private int page;
    private int size;
}
