package com.anshuit.expensemate.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	@GetMapping("/test1")
	public ResponseEntity<String> test1() {
		return ResponseEntity.ok("THIS IS FROM TEST1 METHOD !!");
	}

}
