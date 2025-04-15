package com.anshuit.expensemate.enums;

public enum ApiResponseEnum {
	PROFILE_PICTURE_SUCCESSFULLY_UPDATED("Profile Picture Successfully Updated !!"),
	PROFILE_PICTURE_SUCCESSFULLY_REMOVED("Profile Picture Successfully Removed !!"),
	BOOK_WITH_ID_IS_NOW_PRIMARY("Book With Id : %s , Is Now Primary !!"),
	TRANSACTION_SUCCESSFULLY_DELETED_WITH_ID("Transaction Successfully Deleted With Id : %s !!"),
	REFRESH_TOKEN_SUCCESSFULLY_DELETED("Refresh Token Successfully Deleted !!");

	private final String message;

	private ApiResponseEnum(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
