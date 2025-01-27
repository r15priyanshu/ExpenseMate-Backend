package com.anshuit.expensemate.repositories;

import com.anshuit.expensemate.entities.AppUser;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<AppUser, ObjectId> {
	@Query(value = "{ 'userId': ?0 }", fields = "{ expenses: 0 }")
	Optional<AppUser> findByIdPartial(Object userId);

	@Query(value = "{ 'email': ?0 }", fields = "{ expenses: 0 }")
	Optional<AppUser> findByEmailPartial(String email);

	@Query(value = "{}", fields = "{ expenses: 0 }")
	List<AppUser> findAllPartial();

	Optional<AppUser> findByEmail(String email);
}
