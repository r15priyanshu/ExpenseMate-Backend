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
	public static final String DEFAULT_ROLE_ONE_ID = "aaaaaaaaaaaaaaaaaaaaaaaa";
	public static final String DEFAULT_ROLE_TWO = "ROLE_ADMIN";
	public static final String DEFAULT_ROLE_TWO_ID = "bbbbbbbbbbbbbbbbbbbbbbbb";

	public static final String DEFAULT_USER_ONE_ID = "100000000000000000000001";
	public static final String DEFAULT_USER_TWO_ID = "100000000000000000000002";

	public static final String DEFAULT_CATEGORY_ONE = "GROCERIES";
	public static final String DEFAULT_CATEGORY_ONE_ID = "111111111111111111111111";
	public static final String DEFAULT_CATEGORY_TWO = "SHOPPING";
	public static final String DEFAULT_CATEGORY_TWO_ID = "222222222222222222222222";
	public static final String CUSTOM_CATEGORY_ONE = "RECHARGE";
	public static final String CUSTOM_CATEGORY_ONE_ID = "333333333333333333333333";
	public static final String CUSTOM_CATEGORY_TWO = "INVESTMENT";
	public static final String CUSTOM_CATEGORY_TWO_ID = "444444444444444444444444";

	public static final String JWT_DEFAULT_SECRET = "!!CUSTOM-SECRET-MUST-BE-VERY-LONG-SO-THAT-IT-CANNOT-BE-GUESSED EASILY!!";
	public static final long JWT_TOKEN_VALIDITY_IN_MILLISECONDS = 60 * 1000; // HR,MIN,SEC,MILLI
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
