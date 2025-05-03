package com.anshuit.expensemate.security;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.anshuit.expensemate.constants.GlobalConstants;
import com.anshuit.expensemate.entities.AppUser;
import com.anshuit.expensemate.services.impls.RefreshTokenServiceImpl;
import com.anshuit.expensemate.services.impls.UserServiceImpl;
import com.anshuit.expensemate.utils.JWTUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomOAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	@Lazy
	private UserServiceImpl userService;

	@Autowired
	@Lazy
	private JWTUtil jwtUtil;

	@Autowired
	@Lazy
	private RefreshTokenServiceImpl refreshTokenService;

	public CustomOAuth2AuthenticationSuccessHandler() {
		log.info("CustomOAuth2AuthenticationSuccessHandler Successfully Instantiated !!");
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		log.info("CustomOAuth2AuthenticationSuccessHandler : onAuthenticationSuccess : Executing !!");
		String registrationId = null;
		Map<String, Object> attributes = null;

		if (authentication instanceof OAuth2AuthenticationToken) {
			log.info("OAuth2 : Authentication Successfull !!");
			OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
			registrationId = oauth2Token.getAuthorizedClientRegistrationId();
			attributes = oauth2Token.getPrincipal().getAttributes();

			if (registrationId.equalsIgnoreCase(GlobalConstants.PROVIDER_GITHUB)) {
				log.info("OAuth2 : Provider Name : {}",GlobalConstants.PROVIDER_GITHUB);
				String email = (String) attributes.get("email");
				String firstName = ((String) attributes.get("name")).split(" ")[0];
				String lastName = "";

				AppUser user = userService.getUserByEmailPartialOptional(email).orElseGet(() -> {
					log.info("User Not Registered With Email : {} , Trying To Register.",email);
					AppUser userToBeCreated = new AppUser();
					userToBeCreated.setFirstName(firstName);
					userToBeCreated.setLastName(lastName);
					userToBeCreated.setEmail(email);
					userToBeCreated.setPassword(UUID.randomUUID().toString());
					AppUser createdUser = userService.createUser(userToBeCreated, GlobalConstants.DEFAULT_ROLE_ONE_ID,
							GlobalConstants.PROVIDER_GITHUB);
					log.info("User Successfully Registered.");
					return createdUser;
				});

				// GENERATE THE TOKEN NOW
				log.info("User Details Available , Generating Tokens !!");
				UserDetails userDetails = User.builder().username(user.getEmail()).password(user.getPassword()).authorities(user.getRole()).build();
				String token = jwtUtil.generateToken(userDetails);
				String refrestToken = refreshTokenService.getOrGenerateRefreshToken(user).getRefreshToken();
				
				// FINALLY, REDIRECT TO FRONTEND APPLICATION.
				String redirectUrl = String.format(GlobalConstants.OAUTH2_SUCCESSFUL_LOGIN_REDIRECT_URL_TO_FRONTEND, token,refrestToken);
				response.sendRedirect(redirectUrl);
			}
		}

	}
}
