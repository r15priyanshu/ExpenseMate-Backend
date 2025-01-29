package com.anshuit.expensemate.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.anshuit.expensemate.dtos.ApiResponseDto;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponseDto> handleCustomException(CustomException ex, HttpServletRequest request) {
		ApiResponseDto apiResponseDto = ApiResponseDto
				.builder()
				.message(ex.getMessage())
				.timestamp(LocalDateTime.now())
				.status(ex.getStatus())
				.statusCode(ex.getStatus().value())
				.exceptionCode(ex.getExceptionDetailsEnum() == null ? "" : ex.getExceptionDetailsEnum().getExceptionCode())
				.path(request.getRequestURI())
				.build();

		return new ResponseEntity<ApiResponseDto>(apiResponseDto, ex.getStatus());
	}
}
