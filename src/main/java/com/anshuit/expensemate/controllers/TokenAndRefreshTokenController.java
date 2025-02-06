package com.anshuit.expensemate.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anshuit.expensemate.constants.GlobalConstants;
import com.anshuit.expensemate.dtos.ApiResponseDto;
import com.anshuit.expensemate.dtos.TokenDto;
import com.anshuit.expensemate.enums.ApiResponseEnum;
import com.anshuit.expensemate.enums.ExceptionDetailsEnum;
import com.anshuit.expensemate.exceptions.CustomException;
import com.anshuit.expensemate.services.impls.RefreshTokenServiceImpl;
import com.anshuit.expensemate.utils.JWTUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TokenAndRefreshTokenController {

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private RefreshTokenServiceImpl refreshTokenService;

	@PostMapping(GlobalConstants.REFRESH_TOKEN_BY_USER_ID_URL)
	public ResponseEntity<TokenDto> performTokenRefreshByUserId(@RequestBody TokenDto tokenDto,
			@PathVariable("userId") String userId) {
		TokenDto tokenDtoResponse = refreshTokenService.performTokenRefresh(tokenDto.getRefreshToken(), userId);
		if (tokenDtoResponse == null) {
			throw new CustomException(HttpStatus.UNAUTHORIZED,
					ExceptionDetailsEnum.REFRESH_TOKEN_EXPIRED_WITH_PERFORM_RE_LOGIN_MSG);
		}
		return new ResponseEntity<>(tokenDtoResponse, HttpStatus.OK);
	}

	@PostMapping(GlobalConstants.CHECK_TOKEN_VALIDITY_URL)
	public ResponseEntity<Boolean> checkTokenValidity(@RequestBody TokenDto tokenDto) {
		Boolean isTokenExpired = true;
		try {
			log.info("Validating Token...");
			isTokenExpired = jwtUtil.isTokenExpired(tokenDto.getToken());
		} catch (SignatureException e) {
			log.info(ExceptionDetailsEnum.JWT_SIGNATURE_EXCEPTION_MESSAGE.getExceptionMessage());
		} catch (MalformedJwtException e) {
			log.info(ExceptionDetailsEnum.JWT_MALFORMED_EXCEPTION_MESSAGE.getExceptionMessage());
		} catch (ExpiredJwtException e) {
			log.info(ExceptionDetailsEnum.JWT_EXPIRED_EXCEPTION_MESSAGE.getExceptionMessage());
		}
		Boolean isTokenValid = !isTokenExpired;
		log.info("Is Token Valid : " + isTokenValid);
		return new ResponseEntity<>(isTokenValid, HttpStatus.OK);
	}

	@PostMapping(GlobalConstants.CHECK_REFRESH_TOKEN_VALIDITY_URL)
	public ResponseEntity<Boolean> checkRefreshTokenValidity(@RequestBody TokenDto tokenDto) {
		boolean isRefreshTokenValid = refreshTokenService
				.checkIfRefreshTokenValidFinderPlusOperation(tokenDto.getRefreshToken());
		return new ResponseEntity<>(isRefreshTokenValid, HttpStatus.OK);
	}

	@DeleteMapping(GlobalConstants.DELETE_REFRESH_TOKEN_BY_TOKEN_STRING_IN_REQUEST_BODY_URL)
	public ResponseEntity<ApiResponseDto> deleteRefreshTokenByRefreshTokenString(@RequestBody TokenDto tokenDto,
			HttpServletRequest request) {
		refreshTokenService.deleteRefreshTokenByRefreshTokenStringFinderPlusOperation(tokenDto.getRefreshToken());
		HttpStatus responseStatus = HttpStatus.OK;
		ApiResponseDto apiResponseDto = ApiResponseDto.builder()
				.message(ApiResponseEnum.REFRESH_TOKEN_SUCCESSFULLY_DELETED.getMessage()).timestamp(LocalDateTime.now())
				.status(responseStatus).statusCode(responseStatus.value()).path(request.getRequestURI()).build();
		return new ResponseEntity<>(apiResponseDto, responseStatus);
	}
}
