package com.anshuit.expensemate.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "transactions")
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
	@Id
	private String transactionId;
	private double transactionAmount;
	private LocalDateTime transactionDate;
	private String transactionDescription;
	private String transactionType;
	@DBRef
	private Category category;
	private String userId;
}
