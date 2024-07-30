package com.yuga.yuga.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {

    private String message;

    private Boolean status;

    private String statusCode;

}
