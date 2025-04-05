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
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String exceptionCode;
	
	private HttpStatus status;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String path;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Map<Object, Object> data;
	
	public static ApiResponseDto generateApiResponse(HttpStatus httpStatus, String message) {
		ApiResponseDto apiResponseDto = ApiResponseDto
				.builder()
				.message(message)
				.timestamp(LocalDateTime.now())
				.status(httpStatus)
				.statusCode(httpStatus.value())
				.exceptionCode(null)
				.path(null)
				.build();
		return apiResponseDto;
	}
}