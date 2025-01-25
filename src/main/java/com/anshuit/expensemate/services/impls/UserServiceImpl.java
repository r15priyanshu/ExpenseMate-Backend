package com.anshuit.expensemate.services.impls;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
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

	@Autowired
	private RoleServiceImpl roleService;

	public AppUser saveOrUpdateUser(AppUser appUser) {
		return userRepository.save(appUser);
	}

	public AppUser createUser(AppUser user, String roleId) {
		// First check if user is not already registered.
		Optional<AppUser> optional = this.getUserByEmailOptional(user.getEmail());
		if (optional.isPresent()) {
			throw new CustomException(HttpStatus.BAD_REQUEST, ExceptionDetailsEnum.USER_ALREADY_EXIST_WITH_EMAIL,
					user.getEmail());
		}
		user.setRole(roleService.getRoleById(new ObjectId(roleId)));
		user.setPassword(user.getPassword());
		return this.saveOrUpdateUser(user);
	}

	public Optional<AppUser> getUserByEmailOptional(String email) {
		return userRepository.findByEmail(email);
	}

	public Optional<AppUser> getUserByUserIdOptional(ObjectId userId) {
		return userRepository.findById(userId);
	}

	public AppUser getUserByUserId(ObjectId userId) {
		return this.getUserByUserIdOptional(userId).orElseThrow(() -> {
			throw new CustomException(HttpStatus.NOT_FOUND, ExceptionDetailsEnum.USER_NOT_FOUND_WITH_ID, userId);
		});
	}

	public List<AppUser> getAllUsers() {
		return userRepository.findAll();
	}

	public AppUser deleteUserByUserId(ObjectId userId) {
		AppUser user = this.getUserByUserId(userId);
		userRepository.delete(user);
		return user;
	}
}
