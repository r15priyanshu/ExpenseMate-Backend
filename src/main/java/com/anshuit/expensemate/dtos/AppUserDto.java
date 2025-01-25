package com.anshuit.expensemate.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppUserDto {
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private RoleDto role;
}