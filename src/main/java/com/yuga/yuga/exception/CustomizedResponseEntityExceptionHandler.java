package com.yuga.yuga.exception;

import com.yuga.yuga.payload.response.ApiResponse;
import com.yuga.yuga.util.CommonUtils;
import com.yuga.yuga.util.MessageKeyConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class CustomizedResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;

   @ExceptionHandler(Exception.class)
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   public Mono<ApiResponse> handleException(Exception ex) {
       log.error(ex.getMessage());
       ApiResponse errorResponse = CommonUtils.buildFailureResponse(MessageKeyConstants.INTERNAL_SERVER_ERROR,messageSource, HttpStatus.INTERNAL_SERVER_ERROR, new ApiResponse());
       return Mono.just(errorResponse);
   }

   @ExceptionHandler(ResourceNotFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public Mono<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
       log.error(ex.getMessage());
       ApiResponse errorResponse = CommonUtils.buildFailureResponseResourceNotFound(ex.getMessage(),messageSource, HttpStatus.NOT_FOUND, new ApiResponse());
       return Mono.just(errorResponse);
   }

   @ExceptionHandler(BadRequestException.class)
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   public Mono<ApiResponse> handleBadRequestException(BadRequestException ex) {
       log.error(ex.getMessage());
       ApiResponse errorResponse = CommonUtils.buildFailureResponseBadRequest(ex.getMessage(),messageSource, HttpStatus.BAD_REQUEST, new ApiResponse());
       return Mono.just(errorResponse);
   }
}
