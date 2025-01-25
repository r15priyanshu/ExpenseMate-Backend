package com.anshuit.expensemate.controllers;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anshuit.expensemate.constants.GlobalConstants;
import com.anshuit.expensemate.dtos.AppUserDto;
import com.anshuit.expensemate.entities.AppUser;
import com.anshuit.expensemate.services.impls.DataTransferServiceImpl;
import com.anshuit.expensemate.services.impls.UserServiceImpl;

@RestController
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private DataTransferServiceImpl dataTransferService;

	@PostMapping("/users")
	public ResponseEntity<AppUserDto> createUser(@RequestBody AppUserDto appUserDto) {
		AppUser savedUser = userService.createUser(dataTransferService.mapUserDtoToUser(appUserDto),
				GlobalConstants.DEFAULT_ROLE_ONE_ID);
		AppUserDto savedUserDto = dataTransferService.mapUserToUserDto(savedUser);
		return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<AppUserDto> getUserByUserId(@PathVariable("userId") ObjectId userId) {
		AppUser appUser = userService.getUserByUserId(userId);
		AppUserDto appUserDto = dataTransferService.mapUserToUserDto(appUser);
		return new ResponseEntity<>(appUserDto, HttpStatus.OK);
	}

	@GetMapping("/users")
	public ResponseEntity<List<AppUserDto>> getUAllUsers() {
		List<AppUser> allUsers = userService.getAllUsers();
		List<AppUserDto> allUsersDto = allUsers.stream().map(user -> dataTransferService.mapUserToUserDto(user))
				.toList();
		return new ResponseEntity<>(allUsersDto, HttpStatus.OK);
	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<AppUserDto> deleteUserByUserId(@PathVariable("userId") ObjectId userId) {
		AppUser user = userService.deleteUserByUserId(userId);
		AppUserDto userDto = dataTransferService.mapUserToUserDto(user);
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}
}
