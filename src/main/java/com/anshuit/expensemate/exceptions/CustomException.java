package com.anshuit.expensemate.exceptions;

import org.springframework.http.HttpStatus;

import com.anshuit.expensemate.enums.ExceptionDetailsEnum;
import com.anshuit.expensemate.utils.CustomUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomException extends RuntimeException {
	private static final long serialVersionUID = 8626246725048325965L;

	private ExceptionDetailsEnum exceptionDetailsEnum;
	private HttpStatus status;

	public CustomException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}

	public CustomException(HttpStatus status, ExceptionDetailsEnum exceptionDetailsEnum, Object... args) {
		super(CustomUtil.getFormattedExceptionMessage(exceptionDetailsEnum, args));
		this.exceptionDetailsEnum = exceptionDetailsEnum;
		this.status = status;
	}
}