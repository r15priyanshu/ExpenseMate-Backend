package com.anshuit.expensemate.dtos;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionsDetailsResponseDto {
	private int totalTransactionsCount;
	private double totalDebit;
	private double totalCredit;
	private double total;
	private List<TransactionDto> transactions;
}
