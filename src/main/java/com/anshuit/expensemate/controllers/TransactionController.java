package com.anshuit.expensemate.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anshuit.expensemate.dtos.TransactionDto;
import com.anshuit.expensemate.entities.Transaction;
import com.anshuit.expensemate.services.impls.DataTransferServiceImpl;
import com.anshuit.expensemate.services.impls.TransactionServiceImpl;

@RestController
public class TransactionController {

	@Autowired
	private TransactionServiceImpl transactionService;

	@Autowired
	private DataTransferServiceImpl dataTransferService;

	@PostMapping("/transactions/users/{userId}/category/{categoryId}")
	public ResponseEntity<TransactionDto> createTransaction(@PathVariable("userId") String userId,
			@PathVariable("categoryId") String categoryId, @RequestBody TransactionDto transactionDto) {
		Transaction transaction = dataTransferService.mapTransactionDtoToTransaction(transactionDto);
		Transaction createdTransaction = transactionService.createTransaction(userId, transaction, categoryId);
		TransactionDto createdTransactionDto = dataTransferService.mapTransactionToTransactionDto(createdTransaction);
		return new ResponseEntity<>(createdTransactionDto, HttpStatus.CREATED);
	}

	@GetMapping("/transactions/{transactionId}")
	public ResponseEntity<TransactionDto> getTransactionByTransactionId(
			@PathVariable("transactionId") String transactionId) {
		Transaction transaction = transactionService.getTransactionByTransactionId(transactionId);
		TransactionDto transactionDto = dataTransferService.mapTransactionToTransactionDto(transaction);
		return new ResponseEntity<>(transactionDto, HttpStatus.OK);
	}

	@GetMapping("/transactions/users/{userId}")
	public ResponseEntity<List<TransactionDto>> getAllTransactionsOfUserByUserId(
			@PathVariable("userId") String userId) {
		List<Transaction> allTransactions = transactionService.getAllTransactionsOfUserByUserId(userId);
		List<TransactionDto> allTransactionsDto = allTransactions.stream()
				.map(transaction -> dataTransferService.mapTransactionToTransactionDto(transaction)).toList();
		return new ResponseEntity<>(allTransactionsDto, HttpStatus.OK);
	}

	@GetMapping("/transactions/users/{userId}/year/{year}/month/{month}")
	public ResponseEntity<List<TransactionDto>> getSpecificMonthTransactionsOfUserByUserId(
			@PathVariable("userId") String userId, @PathVariable("year") int year, @PathVariable("month") int month) {
		List<Transaction> allTransactions = transactionService.getSpecificMonthTransactionsOfUserByUserIdInRange(userId, year,
				month);
		List<TransactionDto> allTransactionsDto = allTransactions.stream()
				.map(transaction -> dataTransferService.mapTransactionToTransactionDto(transaction)).toList();
		return new ResponseEntity<>(allTransactionsDto, HttpStatus.OK);
	}
}
