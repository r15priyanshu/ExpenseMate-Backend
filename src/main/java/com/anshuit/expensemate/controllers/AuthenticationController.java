package com.anshuit.expensemate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anshuit.expensemate.constants.GlobalConstants;
import com.anshuit.expensemate.dtos.AppUserDto;
import com.anshuit.expensemate.dtos.LoginRequestDto;
import com.anshuit.expensemate.entities.AppUser;
import com.anshuit.expensemate.exceptions.CustomException;
import com.anshuit.expensemate.services.impls.DataTransferServiceImpl;
import com.anshuit.expensemate.services.impls.RefreshTokenServiceImpl;
import com.anshuit.expensemate.services.impls.UserServiceImpl;
import com.anshuit.expensemate.utils.JWTUtil;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private RefreshTokenServiceImpl refreshTokenService;

	@Autowired
	private DataTransferServiceImpl dataTransferService;

	@PostMapping(GlobalConstants.LOGIN_URL)
	private ResponseEntity<AppUserDto> performLogin(@RequestBody LoginRequestDto loginRequestDto,
			HttpServletResponse response) {
		// Create Unauthenticated Token First
		UsernamePasswordAuthenticationToken unauthenticatedToken = UsernamePasswordAuthenticationToken
				.unauthenticated(loginRequestDto.getEmail(), loginRequestDto.getPassword());

		String token = null;
		AppUser user = null;
		try {
			// PERFORM AUTHENTICATION
			Authentication authenticatedToken = authenticationManager.authenticate(unauthenticatedToken);
			user = (AppUser) authenticatedToken.getDetails();

			// GENERATE THE TOKEN NOW
			UserDetails userDetails = User.builder().username(authenticatedToken.getName()).password(user.getPassword())
					.authorities(user.getRole()).build();
			token = jwtUtil.generateToken(userDetails);

			// IF AUTHENTICATION IS SUCCESS,SET THE AUTHENTICATED TOKEN IN SECURITY-CONTEXT
			SecurityContextHolder.getContext().setAuthentication(authenticatedToken);
		} catch (Exception e) {
			if (e instanceof UsernameNotFoundException) {
				throw new CustomException(e.getMessage(), HttpStatus.NOT_FOUND);
			} else if (e instanceof BadCredentialsException) {
				throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
			} else {
				throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		// Setting The Tokens In Response Header
		response.setHeader(GlobalConstants.JWT_TOKEN_RESPONSE_HEADER_KEY, token);
		response.setHeader(GlobalConstants.JWT_REFRESH_TOKEN_RESPONSE_HEADER_KEY,
				refreshTokenService.getOrGenerateRefreshToken(user).getRefreshToken());
		AppUserDto userDto = dataTransferService.mapUserToUserDto(user);
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}

	@PostMapping(GlobalConstants.REGISTER_URL)
	public ResponseEntity<AppUserDto> registerEmployee(@RequestBody AppUserDto appUserDto) {
		AppUser registeredUser = userService.createUser(dataTransferService.mapUserDtoToUser(appUserDto),
				GlobalConstants.DEFAULT_ROLE_ONE_ID);
		AppUserDto registeredUserDto = dataTransferService.mapUserToUserDto(registeredUser);
		return new ResponseEntity<>(registeredUserDto, HttpStatus.CREATED);
	}
}
