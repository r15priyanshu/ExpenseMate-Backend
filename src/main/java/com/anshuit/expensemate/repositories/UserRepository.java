package com.anshuit.expensemate.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.anshuit.expensemate.entities.AppUser;

public interface UserRepository extends MongoRepository<AppUser, String> {
	@Query(value = "{ 'userId': ?0 }", fields = "{ transactions: 0, books: 0 }")
	Optional<AppUser> findByIdPartial(Object userId);

	@Query(value = "{ 'email': ?0 }", fields = "{ transactions: 0, books: 0 }")
	Optional<AppUser> findByEmailPartial(String email);

	@Query(value = "{}", fields = "{ transactions: 0, books: 0 }")
	List<AppUser> findAllPartial();

	Optional<AppUser> findByEmail(String email);
}
