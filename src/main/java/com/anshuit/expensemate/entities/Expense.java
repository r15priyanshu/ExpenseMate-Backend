package com.anshuit.expensemate.entities;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "expenses")
@Getter
@Setter
@NoArgsConstructor
public class Expense {
	@Id
	private String expenseId;
	private double expenseAmount;
	private LocalDate expenseDate;
	private String expenseDescription;

	@DBRef
	private Category category;
	
    private String userId;
}
