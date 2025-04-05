package com.anshuit.expensemate.dtos;

import java.util.List;

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
	private String profilePic;
	//private String profilePicData;
	private RoleDto role;
	private List<CategoryDto> customExpenseCategories;
	private List<ExpenseDto> expenses;
}