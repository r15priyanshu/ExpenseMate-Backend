package com.anshuit.expensemate.dtos;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDto {
	private String transactionId;
	private double transactionAmount;
	private LocalDateTime transactionDate;
	private String transactionDescription;
	private String transactionType;
	private CategoryDto category;
	private String userId;
	private String bookId;
}
