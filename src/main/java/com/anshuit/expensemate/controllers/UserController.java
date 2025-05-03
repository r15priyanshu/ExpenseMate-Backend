package com.anshuit.expensemate.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anshuit.expensemate.constants.GlobalConstants;
import com.anshuit.expensemate.dtos.ApiResponseDto;
import com.anshuit.expensemate.dtos.AppUserDto;
import com.anshuit.expensemate.entities.AppUser;
import com.anshuit.expensemate.enums.ApiResponseEnum;
import com.anshuit.expensemate.services.impls.DataTransferServiceImpl;
import com.anshuit.expensemate.services.impls.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private DataTransferServiceImpl dataTransferService;

	@PostMapping("/users")
	public ResponseEntity<AppUserDto> createUser(@RequestBody AppUserDto appUserDto) {
		AppUser user = dataTransferService.mapUserDtoToUser(appUserDto);
		AppUser savedUser = userService.createUser(user, GlobalConstants.DEFAULT_ROLE_ONE_ID,
				GlobalConstants.PROVIDER_EXPENSEMATE);
		AppUserDto savedUserDto = dataTransferService.mapUserToUserDto(savedUser);
		return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
	}

	@GetMapping("/users/{userId}")
	@PreAuthorize("hasRole('ADMIN') OR #userId == authentication.details['userId']")
	public ResponseEntity<AppUserDto> getUserByUserId(@PathVariable("userId") String userId) {
		AppUser appUser = userService.getUserByUserId(userId, true);
		AppUserDto appUserDto = dataTransferService.mapUserToUserDto(appUser);
		return new ResponseEntity<>(appUserDto, HttpStatus.OK);
	}

	@GetMapping("/users/email/{email}")
	@PreAuthorize("hasRole('ADMIN') OR #email == authentication.details['email']")
	public ResponseEntity<AppUserDto> getUserByEmail(@PathVariable("email") String email) {
		AppUser appUser = userService.getUserByEmail(email, true);
		AppUserDto appUserDto = dataTransferService.mapUserToUserDto(appUser);
		return new ResponseEntity<>(appUserDto, HttpStatus.OK);
	}

	@GetMapping("/users")
	public ResponseEntity<List<AppUserDto>> getUAllUsers(@RequestParam(name = "fetchPartial") boolean fetchPartial) {
		List<AppUser> allUsers = userService.getAllUsers(fetchPartial);
		List<AppUserDto> allUsersDto = allUsers.stream().map(user -> dataTransferService.mapUserToUserDto(user))
				.toList();
		return new ResponseEntity<>(allUsersDto, HttpStatus.OK);
	}

	@PutMapping("/users/{userId}")
	public ResponseEntity<AppUserDto> updateUserByUserId(@PathVariable("userId") String userId,
			@RequestBody AppUserDto userDto) {
		AppUser appUser = userService.updateUserByUserId(userId, userDto);
		AppUserDto appUserDto = dataTransferService.mapUserToUserDto(appUser);
		return new ResponseEntity<>(appUserDto, HttpStatus.OK);
	}

	@PostMapping("/users/updateProfilePicture/{userId}")
	public ResponseEntity<ApiResponseDto> updateProfilePictureByEmployeeDisplayId(
			@RequestParam("image") MultipartFile image, @PathVariable("userId") String userId,
			HttpServletRequest request) {
		AppUser updatedUser = userService.updateProfilePictureByUserId(image, userId);
		AppUserDto updatedUserDto = dataTransferService.mapUserToUserDto(updatedUser);
		ApiResponseDto apiResponseDto = ApiResponseDto.generateApiResponse(HttpStatus.OK,
				ApiResponseEnum.PROFILE_PICTURE_SUCCESSFULLY_UPDATED.getMessage());
		apiResponseDto.setData(Map.of(GlobalConstants.SINGLE_USER_DATA_KEY, updatedUserDto));
		return new ResponseEntity<ApiResponseDto>(apiResponseDto, HttpStatus.OK);
	}

	@GetMapping("/users/removeProfilePicture/{userId}")
	public ResponseEntity<ApiResponseDto> removeProfilePictureByUserId(@PathVariable("userId") String userId,
			HttpServletRequest request) {
		AppUser updatedUser = userService.removeProfilePictureByUserId(userId);
		AppUserDto updatedUserDto = dataTransferService.mapUserToUserDto(updatedUser);
		ApiResponseDto apiResponseDto = ApiResponseDto.generateApiResponse(HttpStatus.OK,
				ApiResponseEnum.PROFILE_PICTURE_SUCCESSFULLY_REMOVED.getMessage());
		apiResponseDto.setData(Map.of(GlobalConstants.SINGLE_USER_DATA_KEY, updatedUserDto));
		return new ResponseEntity<ApiResponseDto>(apiResponseDto, HttpStatus.OK);
	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<AppUserDto> deleteUserByUserId(@PathVariable("userId") String userId) {
		AppUser user = userService.deleteUserByUserId(userId);
		AppUserDto userDto = dataTransferService.mapUserToUserDto(user);
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}
}
