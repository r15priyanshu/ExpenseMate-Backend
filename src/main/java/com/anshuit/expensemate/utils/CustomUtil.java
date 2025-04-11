package com.anshuit.expensemate.utils;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.anshuit.expensemate.constants.GlobalConstants;
import com.anshuit.expensemate.enums.ApiResponseEnum;
import com.anshuit.expensemate.enums.ExceptionDetailsEnum;

@Component
public class CustomUtil {
	public boolean isImageHavingValidExtensionForProfilePicture(String filename) {
		filename = filename.toLowerCase();
		for (String extension : GlobalConstants.ALLOWED_PROFILE_PICTURE_IMAGE_EXTENSIONS_LIST) {
			if (filename.endsWith(extension)) {
				return true;
			}
		}
		return false;
	}

	public String getFileExtension(String filename) {
		if (filename == null || filename.isEmpty()) {
			return "";
		}

		int lastDotIndex = filename.lastIndexOf('.');
		if (lastDotIndex > 0 && lastDotIndex < filename.length() - 1) {
			return filename.substring(lastDotIndex);
		}

		return "";
	}

	public static String getFormattedExceptionMessage(ExceptionDetailsEnum exceptionDetailsEnum, Object... args) {
		return getFormattedMessage(exceptionDetailsEnum.getExceptionMessage(), args);
	}

	public static String getFormattedApiResponseMessage(ApiResponseEnum apiResponseEnum, Object... args) {
		return getFormattedMessage(apiResponseEnum.getMessage(), args);
	}

	private static String getFormattedMessage(String message, Object... args) {
		if (Objects.isNull(args) || args.length == 0)
			return message;
		return String.format(message, args);
	}
}
