package com.yuga.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class CustomMessageException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String resourceName;

	public CustomMessageException(String resourceName) {
		super(String.format(resourceName));
		this.resourceName = resourceName;
	}


	public String getResourceName() {
		return resourceName;
	}
}
