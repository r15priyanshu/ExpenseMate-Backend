package com.anshuit.expensemate.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.anshuit.expensemate.enums.ExceptionDetailsEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTCustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public JWTCustomAuthenticationProvider() {
		log.info("JWTCustomAuthenticationProvider Successfully Instantiated !!");
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		String password = authentication.getCredentials().toString();
		UserDetails userDetails = null;
		try {
			userDetails = userDetailsService.loadUserByUsername(email);
		} catch (UsernameNotFoundException e) {
			throw e;
		}

		if (passwordEncoder.matches(password, userDetails.getPassword())) {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
			// SETTING COMPLETE USER OBJECT ITSELF IN AUTHENTICATION OJBECT TO PREVENT EXTRA
			// DB CALL LATER
			authenticationToken.setDetails(userDetails);
			return authenticationToken;
		} else {
			throw new BadCredentialsException(ExceptionDetailsEnum.USER_PASSWORD_DID_NOT_MATCH.getExceptionMessage());
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
