package com.anshuit.expensemate.repositories;

import com.anshuit.expensemate.entities.AppUser;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<AppUser, String> {
	Optional<AppUser> findByEmail(String email);
}
