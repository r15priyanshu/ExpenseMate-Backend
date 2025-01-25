package com.anshuit.expensemate.enums;

import java.util.Objects;

public enum ExceptionDetailsEnum {

	// User Related Constants
	USER_NOT_FOUND_WITH_ID("1001", "User not found with id : %s"),

	USER_NOT_FOUND_WITH_EMAIL("1002", "User not found with email : %s"),

	USER_ALREADY_EXIST_WITH_EMAIL("1003", "User already exist with email : %s"),

	// Role Related Constants
	ROLE_NOT_FOUND_WITH_ID("1051", "Role not found with roleId : %s"),

	// Expense Related Constants
	EXPENSE_NOT_FOUND_WITH_ID("1101", "Expense not found with expenseId : %s");

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
