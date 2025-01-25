package com.anshuit.expensemate.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@NoArgsConstructor
public class AppUser {
	@Id
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
}
