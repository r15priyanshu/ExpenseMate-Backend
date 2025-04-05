package com.anshuit.expensemate.controllers;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.anshuit.expensemate.services.impls.UserServiceImpl;

@RestController
public class ImageController {

	@Autowired
	private UserServiceImpl userService;

	@GetMapping(value = "/public/images/serveProfilePicture/users/{userId}")
	public ResponseEntity<byte[]> serveProfilePicture(@PathVariable("userId") String userId) {
		byte[] profilePicData = userService.getUserProfilePicDataByUserId(userId);
		String contentType = new Tika().detect(profilePicData);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(contentType));
		return new ResponseEntity<>(profilePicData, headers, HttpStatus.OK);
	}
}
