package com.anshuit.expensemate.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.anshuit.expensemate.constants.GlobalConstants;
import com.anshuit.expensemate.security.JWTCustomAuthenticationEntryPoint;
import com.anshuit.expensemate.security.JWTCustomTokenValidatorFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {

	@Autowired
	private JWTCustomTokenValidatorFilter jwtCustomTokenValidatorFilter;

	@Autowired
	private JWTCustomAuthenticationEntryPoint jwtCustomAuthenticationEntryPoint;

	@Bean
	PasswordEncoder getPasswordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	AuthenticationManager getAuthenticationManager(AuthenticationConfiguration configuration) throws Exception {
		AuthenticationManager authenticationManager = configuration.getAuthenticationManager();
		return authenticationManager;
	}

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/error", "/test/**", GlobalConstants.LOGIN_URL, GlobalConstants.REGISTER_URL)
				.permitAll().anyRequest().authenticated());
		http.sessionManagement(sessionManagementCustomizer -> sessionManagementCustomizer
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(jwtCustomTokenValidatorFilter, UsernamePasswordAuthenticationFilter.class);
		http.exceptionHandling(ehc -> ehc.authenticationEntryPoint(jwtCustomAuthenticationEntryPoint));
		http.cors(corsCustomizer-> corsCustomizer.configurationSource(createCorsConfigurationSource()));
		http.csrf(csrfCustomizer -> csrfCustomizer.disable());
		http.formLogin(formLoginCustomizer -> formLoginCustomizer.disable());
		http.httpBasic(httpBasicCustomizer -> httpBasicCustomizer.disable());
		return http.build();
	}

	private CorsConfigurationSource createCorsConfigurationSource() {
		CorsConfigurationSource configurationSource = new CorsConfigurationSource() {
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				CorsConfiguration config = new CorsConfiguration();
				config.setAllowedOrigins(GlobalConstants.ALLOWED_ORIGINS_LIST);
				config.setAllowedMethods(GlobalConstants.ALLOWED_METHODS_LIST);
				config.setAllowedHeaders(GlobalConstants.ALLOWED_HEADERS_LIST);
				config.setExposedHeaders(GlobalConstants.EXPOSED_HEADERS_LIST);
				config.setAllowCredentials(true);
				config.setMaxAge(3600L);
				return config;
			}
		};
		return configurationSource;
	}
}
