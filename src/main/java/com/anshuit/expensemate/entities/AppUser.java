package com.anshuit.expensemate.entities;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
public class AppUser {
	@Id
	private ObjectId userId;
	private String firstName;
	private String lastName;

	@Indexed(unique = true)
	private String email;
	private String password;

	@DBRef
	private Role role;

	@DBRef
	private List<Category> customExpenseCategories = new ArrayList<>();

	@DBRef
	private List<Expense> expenses = new ArrayList<>();
}
