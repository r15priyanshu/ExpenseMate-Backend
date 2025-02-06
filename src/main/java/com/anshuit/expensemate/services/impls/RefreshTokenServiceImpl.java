package com.anshuit.expensemate.services.impls;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.anshuit.expensemate.constants.GlobalConstants;
import com.anshuit.expensemate.dtos.TokenDto;
import com.anshuit.expensemate.entities.AppUser;
import com.anshuit.expensemate.entities.RefreshToken;
import com.anshuit.expensemate.enums.ExceptionDetailsEnum;
import com.anshuit.expensemate.exceptions.CustomException;
import com.anshuit.expensemate.repositories.RefreshTokenRepository;
import com.anshuit.expensemate.utils.JWTUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RefreshTokenServiceImpl {
	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private UserServiceImpl userService;

	public TokenDto performTokenRefresh(String refreshToken, String userId) {
		log.info("Performing Token Refresh For UserId : {} : With Refresh Token Value : {}", userId, refreshToken);
		RefreshToken foundRefreshToken = this.findRefreshTokenByRefreshTokenString(refreshToken);
		AppUser foundUser = userService.getUserByUserId(userId, true);

		if (!foundUser.getUserId().equals(foundRefreshToken.getUserId())) {
			throw new CustomException(HttpStatus.BAD_REQUEST,
					ExceptionDetailsEnum.REFRESH_TOKEN_DOES_NOT_BELONG_TO_USER_WITH_ID, userId);
		}

		TokenDto tokenDto = null;
		boolean isRefreshTokenValid = this.checkIfRefreshTokenValidOnlyOperation(foundRefreshToken);
		log.info("Is Refresh Token Valid : {}", isRefreshTokenValid);
		if (isRefreshTokenValid) {
			log.info("Refresh Token Is Valid , Hence Generating New JWT Token For You !!");
			UserDetails userDetails = User.builder().username(foundUser.getEmail()).password(foundUser.getPassword())
					.authorities(foundUser.getRole()).build();
			String token = jwtUtil.generateToken(userDetails);
			tokenDto = new TokenDto();
			tokenDto.setToken(token);
			tokenDto.setRefreshToken(refreshToken);
		} else {
			log.info("Refresh Token Already Expired , Deleting The Token From DB !!");
			this.deleteRefreshTokenByRefreshTokenObjectOnlyOperation(foundRefreshToken);
		}
		return tokenDto;
	}

	public RefreshToken getOrGenerateRefreshToken(AppUser user) {
		Optional<RefreshToken> foundRefreshTokenOptional = this.findRefreshTokenByUserIdOptional(user.getUserId());
		RefreshToken foundRefreshToken = foundRefreshTokenOptional.isPresent() ? foundRefreshTokenOptional.get() : null;
		if (foundRefreshTokenOptional.isEmpty()) {
			log.info("Refresh Token Not Found !! Generating New Refresh Token !!");
			foundRefreshToken = this.createRefreshTokenOnlyOperation(user);
		} else if (this.checkIfRefreshTokenValidOnlyOperation(foundRefreshToken)) {
			log.info("Refresh Token Already Present And Is Valid , Updating The Expiry !!");
			foundRefreshToken.setExpiry(
					new Date(System.currentTimeMillis() + GlobalConstants.JWT_REFRESH_TOKEN_VALIDITY_IN_MILLISECONDS));
		} else {
			log.info(
					"Refresh Token Present But Not Valid !! Updating Old Refresh Token With New Refresh Token and Expiry !!");
			RefreshToken newRefreshTokenDetails = this.createRefreshTokenOnlyOperation(user);
			foundRefreshToken.setRefreshToken(newRefreshTokenDetails.getRefreshToken());
			foundRefreshToken.setExpiry(newRefreshTokenDetails.getExpiry());
		}
		foundRefreshToken = refreshTokenRepository.save(foundRefreshToken);
		return foundRefreshToken;
	}

	public RefreshToken createRefreshTokenOnlyOperation(AppUser user) {
		return RefreshToken.builder().refreshToken(UUID.randomUUID().toString()).userId(user.getUserId())
				.expiry(new Date(
						System.currentTimeMillis() + GlobalConstants.JWT_REFRESH_TOKEN_VALIDITY_IN_MILLISECONDS))
				.build();
	}

	public RefreshToken findRefreshTokenByRefreshTokenString(String refreshToken) {
		RefreshToken foundRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken)
				.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,
						ExceptionDetailsEnum.REFRESH_TOKEN_NOT_FOUND_WITH_TOKEN, refreshToken));
		return foundRefreshToken;
	}

	public RefreshToken findRefreshTokenById(String refreshTokenId) {
		RefreshToken foundRefreshToken = refreshTokenRepository.findById(refreshTokenId)
				.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,
						ExceptionDetailsEnum.REFRESH_TOKEN_NOT_FOUND_WITH_ID, refreshTokenId));
		return foundRefreshToken;
	}

	public Optional<RefreshToken> findRefreshTokenByUserIdOptional(String userId) {
		return refreshTokenRepository.findByUserId(userId);
	}

	public RefreshToken findRefreshTokenByUserId(String userId) {
		RefreshToken foundRefreshToken = this.findRefreshTokenByUserIdOptional(userId)
				.orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,
						ExceptionDetailsEnum.REFRESH_TOKEN_NOT_FOUND_FOR_USER_WITH_USER_ID, userId));
		return foundRefreshToken;
	}

	public boolean checkIfRefreshTokenValidFinderPlusOperation(String refreshToken) {
		RefreshToken foundRefreshToken = findRefreshTokenByRefreshTokenString(refreshToken);
		return this.checkIfRefreshTokenValidOnlyOperation(foundRefreshToken);
	}

	public boolean checkIfRefreshTokenValidOnlyOperation(RefreshToken refreshToken) {
		if (refreshToken == null)
			return false;

		Date current = new Date(System.currentTimeMillis());
		if (current.before(refreshToken.getExpiry())) {
			return true;
		} else {
			return false;
		}
	}

	public void deleteRefreshTokenByRefreshTokenStringFinderPlusOperation(String refreshToken) {
		RefreshToken foundRefreshToken = this.findRefreshTokenByRefreshTokenString(refreshToken);
		this.deleteRefreshTokenByRefreshTokenObjectOnlyOperation(foundRefreshToken);
	}

	public void deleteRefreshTokenByRefreshTokenObjectOnlyOperation(RefreshToken refreshToken) {
		refreshTokenRepository.delete(refreshToken);
	}
}
