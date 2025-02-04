package com.anshuit.expensemate.enums;

import java.util.Objects;

public enum ExceptionDetailsEnum {

	// User Related Constants
	USER_NOT_FOUND_WITH_ID("1001", "User not found with id : %s"),

	USER_NOT_FOUND_WITH_EMAIL("1002", "User not found with email : %s"),

	USER_ALREADY_EXIST_WITH_EMAIL("1003", "User already exist with email : %s"),

	USER_PASSWORD_DID_NOT_MATCH("1004", "Invalid Password !! Password did not match !!"),

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

	JWT_EXPIRED_EXCEPTION_MESSAGE("3002", "Token Already Expired !!");

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
