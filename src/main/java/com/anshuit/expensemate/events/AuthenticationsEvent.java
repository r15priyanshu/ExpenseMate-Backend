package com.anshuit.expensemate.events;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthenticationsEvent {
	@EventListener
	public void handleAuthenticationSuccess(AuthenticationSuccessEvent event) {
		log.info("SUCCESS AUTHENTICATION EVENT : User authenticated: {}", event.getAuthentication().getName());
	}

	@EventListener
	public void handleAuthenticationFailure(AbstractAuthenticationFailureEvent event) {
		log.info("FAILURE AUTHENTICATION EVENT : Authentication failed for user: {} : Reason : {}",
				event.getAuthentication().getName(), event.getException().getMessage());
	}
}
