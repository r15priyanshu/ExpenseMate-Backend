package com.anshuit.expensemate.services.impls;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.anshuit.expensemate.entities.AppUser;
import com.anshuit.expensemate.enums.ExceptionDetailsEnum;
import com.anshuit.expensemate.exceptions.CustomException;
import com.anshuit.expensemate.repositories.UserRepository;

@Service
public class UserServiceImpl {

	@Autowired
	private UserRepository userRepository;

	public AppUser saveOrUpdateUser(AppUser appUser) {
		return userRepository.save(appUser);
	}

	public AppUser createUser(AppUser appUser) {
		// First check if user is not already registered.
		Optional<AppUser> optional = this.getUserByEmailOptional(appUser.getEmail());
		if (optional.isPresent()) {
			throw new CustomException(HttpStatus.BAD_REQUEST, ExceptionDetailsEnum.USER_ALREADY_EXIST_WITH_EMAIL,
					appUser.getEmail());
		}
		appUser.setPassword(appUser.getPassword());
		return this.saveOrUpdateUser(appUser);
	}

	public Optional<AppUser> getUserByEmailOptional(String email) {
		return userRepository.findByEmail(email);
	}

	public Optional<AppUser> getUserByUserIdOptional(String userId) {
		return userRepository.findById(userId);
	}

	public AppUser getUserByUserId(String userId) {
		return this.getUserByUserIdOptional(userId).orElseThrow(() -> {
			throw new CustomException(HttpStatus.NOT_FOUND, ExceptionDetailsEnum.USER_NOT_FOUND_WITH_ID, userId);
		});
	}

	public AppUser deleteUserByUserId(String userId) {
		AppUser user = this.getUserByUserId(userId);
		userRepository.delete(user);
		return user;
	}
}
