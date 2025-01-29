package com.anshuit.expensemate.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTCustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// Set the response status to 401 Unauthorized
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");

		// Create a structured JSON response
		Map<String, Object> errorDetails = new HashMap<>();
		errorDetails.put("status", HttpServletResponse.SC_UNAUTHORIZED);
		errorDetails.put("error", "Unauthorized");
		errorDetails.put("message", authException.getMessage());
		errorDetails.put("path", request.getRequestURI());
		errorDetails.put("timestamp", System.currentTimeMillis());
		errorDetails.put("detail",
				"This is coming from JWTCustomAuthenticationEntryPoint.class , User Not Authenticated At All , Your JWT Token Might Not Be Valid Anymore !! [ Even Anonymous User ( With Authenticated = true Set In The AuthenticationToken ) Can Not Access Secured Resources]");

		// Write JSON response
		response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
	}

}
