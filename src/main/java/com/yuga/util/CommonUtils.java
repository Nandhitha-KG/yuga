package com.yuga.util;

import com.yuga.payload.response.ApiResponse;
import com.yuga.utils.YugaConstants;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

import java.security.SecureRandom;
import java.util.Locale;

public class CommonUtils {

	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final SecureRandom random = new SecureRandom();

	public static <T extends ApiResponse> T buildSuccessResponse(String messageKey, MessageSource messageSource,
                                                                 HttpStatus httpStatus, T response) {
		response = buildResponse(messageKey, MessageKeyConstants.ERROR_CODE_200, messageSource, response);
		response.setStatus(true);
		response.setStatusCode(String.valueOf(httpStatus.value()));
		return response;
	}

	public static <T extends ApiResponse> T buildFailureResponse(String messageKey, MessageSource messageSource,HttpStatus httpStatus, T response) {
		response = buildResponse(messageKey, MessageKeyConstants.ERROR_CODE_500, messageSource, response);
		response.setStatus(false);
		response.setStatusCode(String.valueOf(httpStatus.value()));
		return response;
	}

	public static <T extends ApiResponse> T buildFailureResponseBadRequest(String messageKey, MessageSource messageSource,HttpStatus httpStatus, T response) {
		response = buildResponse(messageKey, MessageKeyConstants.ERROR_CODE_400, messageSource, response);
		response.setStatus(false);
		response.setStatusCode(String.valueOf(httpStatus.value()));
		return response;
	}


	public static <T extends ApiResponse> T buildFailureResponseResourceNotFound(String messageKey, MessageSource messageSource,HttpStatus httpStatus, T response) {
		response = buildResponse(messageKey, MessageKeyConstants.ERROR_CODE_404, messageSource, response);
		response.setStatus(false);
		response.setStatusCode(String.valueOf(httpStatus.value()));
		return response;
	}

	private static <T extends ApiResponse> T buildResponse(String messageKey, String responseCodeKey,
			MessageSource messageSource, T response) {
		Locale locale = getLocaly();
		response.setMessage(getMessageInfo(locale, messageKey, messageSource));
		response.setStatusCode(getMessageInfo(locale, responseCodeKey, messageSource));
		return response;
	}

	public static String getMessageInfo(Locale locale, String messageKey, MessageSource messageSource) {
		LocalizationMessageUtil instance = LocalizationMessageUtil.getInstance();
		return instance.getMessageInfo(locale, messageKey, messageSource);
	}

	public static Locale getLocaly() {
		Locale locale = LocaleContextHolder.getLocale();
		locale = new Locale(YugaConstants.ENGLISH, YugaConstants.COUNTRY);
		return locale;
	}

	public static String generateToken(int tokenLength) {
		StringBuilder token = new StringBuilder(tokenLength);
		for (int i = 0; i < tokenLength; i++) {
			int index = random.nextInt(CHARACTERS.length());
			token.append(CHARACTERS.charAt(index));
		}
		return token.toString();
	}

}
