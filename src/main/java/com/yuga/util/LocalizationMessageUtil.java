package com.yuga.util;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class LocalizationMessageUtil {

	private static LocalizationMessageUtil instance;

	private LocalizationMessageUtil() {
		// Initialization code, if needed
	}

	public static LocalizationMessageUtil getInstance() {
		if (instance == null) {
			instance = new LocalizationMessageUtil();
		}
		return instance;
	}

	public String getMessageInfo(Locale locale, String messageKey, MessageSource messageSource) {
		return messageSource.getMessage(messageKey, null, locale);
	}

}
