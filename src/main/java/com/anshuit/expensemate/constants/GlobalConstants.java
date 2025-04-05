package com.anshuit.expensemate.constants;

import java.util.List;
import java.util.Set;

public class GlobalConstants {
	private GlobalConstants() {
	}

	public static final String DEFAULT_APPLICATION_NAME = "ExpenseMate";
	public static final String DEFAULT_FRONTEND_APPLICATION_NAME = "ExpenseMate Frontend";
	public static final String DEFAULT_FRONTEND_ORIGIN_URL = "http://localhost:4200";

	public static final String DEFAULT_PROFILE_PIC_FILE_NAME = "default.png";

	public static final String DEFAULT_ROLE_ONE = "ROLE_NORMAL";
	public static final String DEFAULT_ROLE_ONE_ID = "1";
	public static final String DEFAULT_ROLE_TWO = "ROLE_ADMIN";
	public static final String DEFAULT_ROLE_TWO_ID = "2";

	public static final String DEFAULT_USER_ONE_ID = "1";
	public static final String DEFAULT_USER_TWO_ID = "2";

	public static final String CATEGORY_TYPE_DEBIT = "DEBIT";
	public static final String CATEGORY_TYPE_CREDIT = "CREDIT";
	public static final String CATEGORY_OWNER_SYSTEM = "SYSTEM";
	
	public static final String DEFAULT_CATEGORY_ONE = "SALARY";
	public static final String DEFAULT_CATEGORY_ONE_ID = "1";
	public static final String DEFAULT_CATEGORY_TWO = "SHOPPING";
	public static final String DEFAULT_CATEGORY_TWO_ID = "2";
	public static final String CUSTOM_CATEGORY_ONE = "RECHARGE";
	public static final String CUSTOM_CATEGORY_ONE_ID = "3";
	public static final String CUSTOM_CATEGORY_TWO = "INVESTMENT";
	public static final String CUSTOM_CATEGORY_TWO_ID = "4";

	public static final String JWT_DEFAULT_SECRET = "!!MY-CUSTOM-SECRET-KEY-FOR-JWT!!";
	public static final String JWT_TOKEN_RESPONSE_HEADER_KEY = "JWT-TOKEN";
	public static final String JWT_REFRESH_TOKEN_RESPONSE_HEADER_KEY = "JWT-REFRESH-TOKEN";
	public static final long JWT_TOKEN_VALIDITY_IN_MILLISECONDS = 1 * 60 * 60 * 1000; // HR,MIN,SEC,MILLI
	public static final long JWT_REFRESH_TOKEN_VALIDITY_IN_MILLISECONDS = 1 * 60 * 1000; // HR,MIN,SEC,MILLI

	public static final String LOGIN_URL = "/auth/login";
	public static final String REGISTER_URL = "/auth/register";
	public static final String CHECK_TOKEN_VALIDITY_URL = "/public/tokenAndRefreshToken/validateToken";
	public static final String REFRESH_TOKEN_BY_USER_ID_URL = "/public/tokenAndRefreshToken/refreshToken/user/{userId}";
	public static final String CHECK_REFRESH_TOKEN_VALIDITY_URL = "/public/tokenAndRefreshToken/validateRefreshToken";
	public static final String DELETE_REFRESH_TOKEN_BY_TOKEN_STRING_IN_REQUEST_BODY_URL = "/public/tokenAndRefreshToken/deleteRefreshToken";

	public static final List<String> ALLOWED_ORIGINS_LIST = List.of(DEFAULT_FRONTEND_ORIGIN_URL);
	public static final List<String> ALLOWED_HEADERS_LIST = List.of("*");
	public static final List<String> ALLOWED_METHODS_LIST = List.of("GET", "PUT", "POST", "DELETE", "OPTIONS");
	public static final List<String> EXPOSED_HEADERS_LIST = List.of("*");

	public static final String EXTENSION_JPG = ".jpg";
	public static final String EXTENSION_JPEG = ".jpeg";
	public static final String EXTENSION_PNG = ".png";
	public static final String EXTENSION_GIF = ".gif";
	public static final List<String> ALLOWED_PROFILE_PICTURE_IMAGE_EXTENSIONS_LIST = List.of(EXTENSION_JPG,
			EXTENSION_JPEG, EXTENSION_PNG, EXTENSION_GIF);
	
	//KEY TO BE USED IN API-RESPONSE-DTO
	public static final String SINGLE_USER_DATA_KEY = "user";

	// SET OF URLS FOR WHICH JWT TOKEN VALIDATOR FILTER SHOULD NOT RUN
	public static final Set<String> EXCLUDED_PATHS_FOR_JWT_TOKEN_VALIDATOR_FILTER_SET = Set.of(
			GlobalConstants.LOGIN_URL, GlobalConstants.REGISTER_URL, GlobalConstants.CHECK_TOKEN_VALIDITY_URL,
			GlobalConstants.CHECK_REFRESH_TOKEN_VALIDITY_URL, GlobalConstants.REFRESH_TOKEN_BY_USER_ID_URL,
			GlobalConstants.DELETE_REFRESH_TOKEN_BY_TOKEN_STRING_IN_REQUEST_BODY_URL);
}
