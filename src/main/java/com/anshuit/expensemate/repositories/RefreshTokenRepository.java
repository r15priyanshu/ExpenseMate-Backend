package com.anshuit.expensemate.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anshuit.expensemate.entities.RefreshToken;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
	Optional<RefreshToken> findByRefreshToken(String refreshToken);

	Optional<RefreshToken> findByUserId(String userId);
}
