package com.anshuit.expensemate.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.anshuit.expensemate.constants.GlobalConstants;
import com.anshuit.expensemate.enums.ExceptionDetailsEnum;
import com.anshuit.expensemate.utils.JWTUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTCustomTokenValidatorFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JWTUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("Inside JwtTokenValidatorFilter !! URL : " + request.getServletPath());

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			log.info("User Already Authenticated !! Just Invoking Next Filter In The Chain. !!");
			filterChain.doFilter(request, response);
		}

		String email = null;
		boolean exceptionOccurred = false;
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			log.info("Authorization Header With Bearer Token Present !!");
			String tokenWithoutBearer = authorizationHeader.substring(7);
			log.info("Token Without Bearer : {}", tokenWithoutBearer);
			try {
				log.info("Validating And Extracting Username From Token.");
				email = jwtUtil.extractUsername(tokenWithoutBearer);
				log.info("Token Is Valid And Username Extracted From Token : {}", email);
			} catch (MalformedJwtException e) {
				exceptionOccurred = true;
				log.info(ExceptionDetailsEnum.JWT_MALFORMED_EXCEPTION_MESSAGE.getExceptionMessage());
			} catch (ExpiredJwtException e) {
				exceptionOccurred = true;
				log.info(ExceptionDetailsEnum.JWT_EXPIRED_EXCEPTION_MESSAGE.getExceptionMessage());
			}

			if (!exceptionOccurred) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(email);
				log.info("Token Is Valid !! User Successfully Authenticated !!");
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails.getUsername(), null, userDetails.getAuthorities());
				// SETTING COMPLETE USER OBJECT ITSELF IN AUTHENTICATION OJBECT TO FURTHER CHECK IN METHOD LEVEL SECURITY
				authenticationToken.setDetails(userDetails);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		} else {
			log.info(
					"Authorization Header is either empty or Does not starts With Bearer !! Invoking Next Filter in the Chain !!");
		}

		// Should Always Execute
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return GlobalConstants.EXCLUDED_PATHS_FOR_JWT_TOKEN_VALIDATOR_FILTER_SET.contains(request.getServletPath());
	}
}
