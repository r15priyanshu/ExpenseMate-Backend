package com.anshuit.expensemate.dtos;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseDto {
	private String expenseId;
	private double expenseAmount;
	private LocalDate expenseDate;
	private String expenseDescription;
	private CategoryDto category;
	private String userId;
}
