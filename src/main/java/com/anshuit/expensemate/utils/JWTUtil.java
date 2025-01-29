package com.anshuit.expensemate.utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.anshuit.expensemate.constants.GlobalConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

	private static String SECRET_KEY = GlobalConstants.JWT_DEFAULT_SECRET;
	private static long TOKEN_VALIDITY_IN_MILLISECONDS = GlobalConstants.JWT_TOKEN_VALIDITY_IN_MILLISECONDS;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", userDetails.getAuthorities().stream().map(authority -> authority.getAuthority())
				.collect(Collectors.joining(",")));
		return createToken(claims, userDetails.getUsername());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.header().add("typ", "JWT").and()
				.issuer(GlobalConstants.DEFAULT_APPLICATION_NAME)
				.subject(subject)
				.audience().add(GlobalConstants.DEFAULT_FRONTEND_APPLICATION_NAME).and()
				.issuedAt(new Date())
				.notBefore(new Date())
				.expiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_IN_MILLISECONDS))
				.id(UUID.randomUUID().toString())
				.claims(claims)
				.signWith(getKey())
				.compact();
	}

	private SecretKey getKey() {
		byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
		SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);
		return secretKey;
	}

	private Claims extractAllClaims(String token) {
		JwtParser jwtParser = Jwts.parser().verifyWith(getKey()).build();
		Jws<Claims> signedClaims = jwtParser.parseSignedClaims(token);
		return signedClaims.getPayload();
	}
}
