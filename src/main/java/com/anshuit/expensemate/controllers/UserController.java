package com.anshuit.expensemate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anshuit.expensemate.dtos.AppUserDto;
import com.anshuit.expensemate.entities.AppUser;
import com.anshuit.expensemate.services.impls.DataTransferServiceImpl;
import com.anshuit.expensemate.services.impls.UserServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private DataTransferServiceImpl dataTransferService;

	@PostMapping
	public ResponseEntity<AppUserDto> createUser(@RequestBody AppUserDto appUserDto) {
		AppUser savedUser = userService.createUser(dataTransferService.mapAppUserDtoToAppUser(appUserDto));
		AppUserDto savedUserDto = dataTransferService.mapAppUserToAppUserDto(savedUser);
		return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
	}
}
