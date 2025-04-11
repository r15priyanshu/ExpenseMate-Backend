package com.anshuit.expensemate.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupedTransactionDto {
	private LocalDate transactionDate;
	private List<TransactionDto> transactions;
}
