package com.yuga.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExpiredTokenException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExpiredTokenException(String message) {
		super(message);
	}

	public ExpiredTokenException(String message, Throwable cause) {
		super(message, cause);
	}
}
