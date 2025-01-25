package com.anshuit.expensemate.repositories;

import com.anshuit.expensemate.entities.AppUser;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<AppUser, ObjectId> {
	Optional<AppUser> findByEmail(String email);
}
