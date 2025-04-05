package com.anshuit.expensemate.dtos;

import java.time.LocalDate;

import com.anshuit.expensemate.entities.Category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDto {
	private String transactionId;
	private double transactionAmount;
	private LocalDate transactionDate;
	private String transactionDescription;
	private String transactionType;
	private Category category;
	private String userId;
}
