package com.anshuit.expensemate.enums;

import java.util.Objects;

import com.anshuit.expensemate.constants.GlobalConstants;

public enum ExceptionDetailsEnum {

	// User Related Constants
	USER_NOT_FOUND_WITH_ID("1001", "User Not Found With UserId : %s"),

	USER_NOT_FOUND_WITH_EMAIL("1002", "User Not Found With Email : %s"),

	USER_ALREADY_EXIST_WITH_EMAIL("1003", "User Already Exist With Email : %s"),

	USER_PASSWORD_DID_NOT_MATCH("1004", "Invalid Password !! Password Did Not Match !!"),

	// Role Related Constants
	ROLE_NOT_FOUND_WITH_ID("1051", "Role not found with roleId : %s"),

	// Expense Related Constants
	EXPENSE_NOT_FOUND_WITH_ID("1101", "Expense not found with expenseId : %s"),

	// Category Related Constants
	CATEGORY_NOT_FOUND_WITH_ID("1150", "Category not found with categoryId : %s"),

	DEFAULT_EXPENSE_CATEGORY_NOT_FOUND_WITH_ID("1151", "Default Expense Category not found with categoryId : %s"),

	CUSTOM_EXPENSE_CATEGORY_NOT_FOUND_WITH_ID("1152", "Custom Expense Category not found with categoryId : %s"),

	// JWT Related Constants
	JWT_MALFORMED_EXCEPTION_MESSAGE("3001", "Token Malformed !! Token Might Have Been Tampered !!"),

	JWT_EXPIRED_EXCEPTION_MESSAGE("3002", "Token Already Expired !!"),
	
	JWT_SIGNATURE_EXCEPTION_MESSAGE("3003", "JWT Signature Does Not Match Locally Computed signature !! Token Might Have Been Tampered !!"),
	
	REFRESH_TOKEN_NOT_FOUND_WITH_ID("3004", "Refresh Token Not Found With Id : %s"),
	
	REFRESH_TOKEN_NOT_FOUND_FOR_USER_WITH_USER_ID("3004", "Refresh Token Not Found For User With UserId : %s"),

	REFRESH_TOKEN_NOT_FOUND_WITH_TOKEN("3005", "Refresh Token Not Found With Token Value : %s"),

	REFRESH_TOKEN_EXPIRED_WITH_PERFORM_RE_LOGIN_MSG("3006", "Refresh Token Already Expired !! Please Re-Login !!"),
	
	REFRESH_TOKEN_DOES_NOT_BELONG_TO_USER_WITH_ID("4007", "Refresh Token Does Not Belong To User With Id : %s !!"),
	
	// Other Constants
	PROFILE_PICTURE_NOT_PRESENT("5001", "Profile Picture Not Present !!"),

	ERROR_IN_UPDATING_PROFILE_PICTURE("5002", "Error In Uploading Profile Picture !!"),

	NOT_AN_ALLOWED_IMAGE_EXTENSION("5003", "Not An Allowed Image Extension !! Allowed Extensions Are : " + GlobalConstants.ALLOWED_PROFILE_PICTURE_IMAGE_EXTENSIONS_LIST),;

	private final String exceptionCode;
	private final String exceptionMessage;

	private ExceptionDetailsEnum(String exceptionCode, String exceptionMessage) {
		this.exceptionCode = exceptionCode;
		this.exceptionMessage = exceptionMessage;
	}

	public String getExceptionCode() {
		return exceptionCode;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public static String getFormattedExceptionMessage(ExceptionDetailsEnum exceptionDetailsEnum, Object... args) {
		if (Objects.isNull(args) || args.length == 0)
			return exceptionDetailsEnum.getExceptionMessage();

		return String.format(exceptionDetailsEnum.getExceptionMessage(), args);
	}
}
