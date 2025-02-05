package com.anshuit.expensemate.services.impls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	@Autowired
	private PasswordEncoder passwordEncoder;

	public AppUser saveOrUpdateUser(AppUser appUser) {
		return userRepository.save(appUser);
	}

	public AppUser createUser(AppUser user, String roleId) {
		// First check if user is not already registered.
		Optional<AppUser> userOptional = this.getUserByEmailOptional(user.getEmail());
		if (userOptional.isPresent()) {
			throw new CustomException(HttpStatus.BAD_REQUEST, ExceptionDetailsEnum.USER_ALREADY_EXIST_WITH_EMAIL,
					user.getEmail());
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(roleService.getRoleById(roleId));
		user.setCustomExpenseCategories(new ArrayList<>());
		user.setExpenses(new ArrayList<>());
		return this.saveOrUpdateUser(user);
	}

	public Optional<AppUser> getUserByUserIdOptional(String userId) {
		return userRepository.findById(userId);
	}

	public Optional<AppUser> getUserByUserIdPartilOptional(String userId) {
		return userRepository.findByIdPartial(userId);
	}

	public AppUser getUserByUserId(String userId, boolean fetchPartial) {
		if (fetchPartial) {
			return this.getUserByUserIdPartial(userId);
		} else {
			return this.getUserByUserId(userId);
		}
	}

	public Optional<AppUser> getUserByEmailOptional(String email) {
		return userRepository.findByEmail(email);
	}

	public Optional<AppUser> getUserByEmailPartialOptional(String email) {
		return userRepository.findByEmailPartial(email);
	}

	public AppUser getUserByEmail(String email, boolean fetchPartial) {
		if (fetchPartial) {
			return this.getUserByEmailPartial(email);
		} else {
			return this.getUserByEmail(email);
		}
	}

	public List<AppUser> getAllUsers(boolean fetchPartial) {
		if (fetchPartial) {
			return userRepository.findAllPartial();
		} else {
			return userRepository.findAll();
		}
	}

	public AppUser deleteUserByUserId(String userId) {
		AppUser user = this.getUserByUserId(userId);
		userRepository.delete(user);
		return user;
	}

	private AppUser getUserByUserId(String userId) {
		return this.getUserByUserIdOptional(userId).orElseThrow(() -> {
			throw new CustomException(HttpStatus.NOT_FOUND, ExceptionDetailsEnum.USER_NOT_FOUND_WITH_ID, userId);
		});
	}

	private AppUser getUserByUserIdPartial(String userId) {
		return this.getUserByUserIdPartilOptional(userId).orElseThrow(() -> {
			throw new CustomException(HttpStatus.NOT_FOUND, ExceptionDetailsEnum.USER_NOT_FOUND_WITH_ID, userId);
		});
	}

	private AppUser getUserByEmail(String email) {
		return this.getUserByEmailOptional(email).orElseThrow(() -> {
			throw new CustomException(HttpStatus.NOT_FOUND, ExceptionDetailsEnum.USER_NOT_FOUND_WITH_EMAIL, email);
		});
	}

	private AppUser getUserByEmailPartial(String email) {
		return this.getUserByEmailPartialOptional(email).orElseThrow(() -> {
			throw new CustomException(HttpStatus.NOT_FOUND, ExceptionDetailsEnum.USER_NOT_FOUND_WITH_EMAIL, email);
		});
	}
}
