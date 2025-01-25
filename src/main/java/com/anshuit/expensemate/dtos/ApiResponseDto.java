package com.anshuit.expensemate.dtos;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiResponseDto {
	private LocalDateTime timestamp;
	private int statusCode;
	private String message;
	private String exceptionCode;
	private HttpStatus status;
	private String path;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Map<Object, Object> data;
}