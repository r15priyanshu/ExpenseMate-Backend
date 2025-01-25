package com.anshuit.expensemate.dtos;

import java.time.LocalDate;

import com.anshuit.expensemate.entities.AppUser;

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
	private AppUser user;
}
