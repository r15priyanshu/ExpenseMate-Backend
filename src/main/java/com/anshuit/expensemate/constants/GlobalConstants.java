package com.anshuit.expensemate.constants;

import java.util.List;
import java.util.Set;

public class GlobalConstants {
	private GlobalConstants() {
	}

	public static final String DEFAULT_APPLICATION_NAME = "ExpenseMate";
	public static final String DEFAULT_FRONTEND_APPLICATION_NAME = "ExpenseMate Frontend";
	public static final String DEFAULT_FRONTEND_ORIGIN_URL = "http://localhost:4200";

	public static final String DEFAULT_ROLE_ONE = "ROLE_NORMAL";
	public static final String DEFAULT_ROLE_ONE_ID = "1";
	public static final String DEFAULT_ROLE_TWO = "ROLE_ADMIN";
	public static final String DEFAULT_ROLE_TWO_ID = "2";

	public static final String DEFAULT_USER_ONE_ID = "1";
	public static final String DEFAULT_USER_TWO_ID = "2";

	public static final String DEFAULT_CATEGORY_ONE = "GROCERIES";
	public static final String DEFAULT_CATEGORY_ONE_ID = "1";
	public static final String DEFAULT_CATEGORY_TWO = "SHOPPING";
	public static final String DEFAULT_CATEGORY_TWO_ID = "2";
	public static final String CUSTOM_CATEGORY_ONE = "RECHARGE";
	public static final String CUSTOM_CATEGORY_ONE_ID = "3";
	public static final String CUSTOM_CATEGORY_TWO = "INVESTMENT";
	public static final String CUSTOM_CATEGORY_TWO_ID = "4";

	public static final String JWT_DEFAULT_SECRET = "!!CUSTOM-SECRET-MUST-BE-VERY-LONG-SO-THAT-IT-CANNOT-BE-GUESSED EASILY!!";
	public static final long JWT_TOKEN_VALIDITY_IN_MILLISECONDS = 15 * 1000; // HR,MIN,SEC,MILLI
	public static final String JWT_TOKEN_RESPONSE_HEADER_KEY = "JWT-TOKEN";

	public static final String LOGIN_URL = "/auth/login";
	public static final String REGISTER_URL = "/auth/register";
	
	public static final List<String> ALLOWED_ORIGINS_LIST = List.of(DEFAULT_FRONTEND_ORIGIN_URL);
	public static final List<String> ALLOWED_HEADERS_LIST = List.of("*");
	public static final List<String> ALLOWED_METHODS_LIST = List.of("GET", "PUT", "POST", "DELETE", "OPTIONS");
	public static final List<String> EXPOSED_HEADERS_LIST = List.of("*");

	// SET OF URLS FOR WHICH JWT TOKEN VALIDATOR FILTER SHOULD NOT RUN
	public static final Set<String> EXCLUDED_PATHS_FOR_JWT_TOKEN_VALIDATOR_FILTER_SET = Set
			.of(GlobalConstants.LOGIN_URL, GlobalConstants.REGISTER_URL);
}
