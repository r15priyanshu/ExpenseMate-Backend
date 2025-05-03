package com.anshuit.expensemate.services.impls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.anshuit.expensemate.constants.GlobalConstants;
import com.anshuit.expensemate.dtos.AppUserDto;
import com.anshuit.expensemate.entities.AppUser;
import com.anshuit.expensemate.enums.ExceptionDetailsEnum;
import com.anshuit.expensemate.exceptions.CustomException;
import com.anshuit.expensemate.repositories.UserRepository;
import com.anshuit.expensemate.utils.CustomUtil;

@Service
public class UserServiceImpl {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleServiceImpl roleService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomUtil customUtil;

	public AppUser saveOrUpdateUser(AppUser appUser) {
		return userRepository.save(appUser);
	}

	public AppUser createUser(AppUser user, String roleId, String provider) {
		// First Check If User Is Not Already Registered.
		Optional<AppUser> userOptional = this.getUserByEmailOptional(user.getEmail());
		if (userOptional.isPresent()) {
			throw new CustomException(HttpStatus.BAD_REQUEST, ExceptionDetailsEnum.USER_ALREADY_EXIST_WITH_EMAIL,
					user.getEmail());
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setProfilePic(GlobalConstants.DEFAULT_PROFILE_PIC_FILE_NAME);
		user.setProvider(provider);
		user.setRole(roleService.getRoleById(roleId));
		user.setTransactions(new ArrayList<>());
		return this.saveOrUpdateUser(user);
	}

	public Optional<AppUser> getUserByUserIdOptional(String userId) {
		return userRepository.findById(userId);
	}

	public Optional<AppUser> getUserByUserIdPartialOptional(String userId) {
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

	public AppUser updateUserByUserId(String userId, AppUserDto userDto) {
		AppUser foundUser = this.getUserByUserId(userId, true);
		foundUser.setFirstName(userDto.getFirstName());
		foundUser.setLastName(userDto.getLastName());
		return saveOrUpdateUser(foundUser);
	}

	public AppUser updateProfilePictureByUserId(MultipartFile file, String userId) {
		AppUser user = this.getUserByUserId(userId, true);
		String filename = file.getOriginalFilename();
		if (file != null && customUtil.isImageHavingValidExtensionForProfilePicture(filename)) {
			try {
				byte[] imageData = file.getBytes();
				String profilePicName = UUID.randomUUID().toString().concat(customUtil.getFileExtension(filename));
				user.setProfilePicData(imageData);
				user.setProfilePic(profilePicName);
			} catch (Exception e) {
				throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR,
						ExceptionDetailsEnum.ERROR_IN_UPDATING_PROFILE_PICTURE);
			}
		} else {
			throw new CustomException(HttpStatus.BAD_REQUEST, ExceptionDetailsEnum.NOT_AN_ALLOWED_IMAGE_EXTENSION);
		}
		return this.saveOrUpdateUser(user);
	}

	public AppUser removeProfilePictureByUserId(String userId) {
		AppUser user = this.getUserByUserId(userId, true);
		if (user == null || user.getProfilePic().equals(GlobalConstants.DEFAULT_PROFILE_PIC_FILE_NAME)) {
			throw new CustomException(HttpStatus.BAD_REQUEST, ExceptionDetailsEnum.PROFILE_PICTURE_NOT_PRESENT);
		}
		user.setProfilePic(GlobalConstants.DEFAULT_PROFILE_PIC_FILE_NAME);
		user.setProfilePicData(null);
		return this.saveOrUpdateUser(user);
	}

	public byte[] getUserProfilePicDataByUserId(String userId) {
		AppUser user = this.getUserByUserId(userId);
		if (user == null || user.getProfilePicData() == null) {
			throw new CustomException(HttpStatus.BAD_REQUEST, ExceptionDetailsEnum.PROFILE_PICTURE_NOT_PRESENT);
		}
		return user.getProfilePicData();
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
		return this.getUserByUserIdPartialOptional(userId).orElseThrow(() -> {
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
