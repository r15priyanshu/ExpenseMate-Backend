package com.anshuit.expensemate.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anshuit.expensemate.constants.GlobalConstants;
import com.anshuit.expensemate.dtos.ApiResponseDto;
import com.anshuit.expensemate.dtos.GroupedTransactionDto;
import com.anshuit.expensemate.dtos.TransactionDto;
import com.anshuit.expensemate.dtos.TransactionsDetailsResponseDto;
import com.anshuit.expensemate.entities.Transaction;
import com.anshuit.expensemate.enums.ApiResponseEnum;
import com.anshuit.expensemate.services.impls.DataTransferServiceImpl;
import com.anshuit.expensemate.services.impls.TransactionServiceImpl;
import com.anshuit.expensemate.utils.CustomUtil;

@RestController
public class TransactionController {

	@Autowired
	private TransactionServiceImpl transactionService;

	@Autowired
	private DataTransferServiceImpl dataTransferService;

	@PostMapping("/transactions/books/{bookId}/users/{userId}/category/{categoryId}")
	public ResponseEntity<TransactionDto> createTransaction(@PathVariable("bookId") String bookId,
			@PathVariable("userId") String userId, @PathVariable("categoryId") String categoryId,
			@RequestBody TransactionDto transactionDto) {
		Transaction transaction = dataTransferService.mapTransactionDtoToTransaction(transactionDto);
		Transaction createdTransaction = transactionService.createTransaction(bookId, userId, transaction, categoryId);
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

	@GetMapping("/transactions/books/{bookId}/users/{userId}")
	public ResponseEntity<List<TransactionDto>> getAllTransactionsOfUserByUserIdForSpecificBook(
			@PathVariable("bookId") String bookId, @PathVariable("userId") String userId) {
		List<Transaction> allTransactions = transactionService.getAllTransactionsOfUserByUserIdForSpecificBook(bookId,
				userId);
		List<TransactionDto> allTransactionsDto = allTransactions.stream()
				.map(transaction -> dataTransferService.mapTransactionToTransactionDto(transaction)).toList();
		return new ResponseEntity<>(allTransactionsDto, HttpStatus.OK);
	}

	@GetMapping("/transactions/books/{bookId}/users/{userId}/year/{year}/month/{month}")
	public ResponseEntity<TransactionsDetailsResponseDto> getSpecificMonthTransactionsOfUserByUserIdForSpecificBook(
			@PathVariable("bookId") String bookId, @PathVariable("userId") String userId,
			@PathVariable("year") int year, @PathVariable("month") int month) {
		List<Transaction> allTransactions = transactionService
				.getSpecificMonthTransactionsOfUserByUserIdForSpecificBookInRange(bookId, userId, year, month);
		List<TransactionDto> allTransactionsDto = allTransactions.stream()
				.map(transaction -> dataTransferService.mapTransactionToTransactionDto(transaction)).toList();

		Map<String, Double> transactionsValue = allTransactionsDto.stream().collect(Collectors.groupingBy(
				TransactionDto::getTransactionType, Collectors.summingDouble(TransactionDto::getTransactionAmount)));
		Double totalCredit = transactionsValue.getOrDefault(GlobalConstants.CATEGORY_TYPE_CREDIT, 0.0);
		Double totalDebit = transactionsValue.getOrDefault(GlobalConstants.CATEGORY_TYPE_DEBIT, 0.0);
		Double total = totalCredit - totalDebit;

		TransactionsDetailsResponseDto transactionsDetailsResponseDto = new TransactionsDetailsResponseDto();
		transactionsDetailsResponseDto.setTotalTransactionsCount(allTransactionsDto.size());
		transactionsDetailsResponseDto.setTotalCredit(totalCredit);
		transactionsDetailsResponseDto.setTotalDebit(totalDebit);
		transactionsDetailsResponseDto.setTotal(total);

		Map<LocalDate, List<TransactionDto>> groupedTransactionsMap = allTransactionsDto.stream()
				.collect(Collectors.groupingBy(transactionDto -> {
					return transactionDto.getTransactionDate().toLocalDate();
				}));

		List<GroupedTransactionDto> groupedTransactions = groupedTransactionsMap.entrySet().stream().map(entry -> {
			GroupedTransactionDto groupedTransactionDto = new GroupedTransactionDto();
			groupedTransactionDto.setTransactionDate(entry.getKey());
			groupedTransactionDto.setTransactions(entry.getValue());
			return groupedTransactionDto;
		}).toList();

		transactionsDetailsResponseDto.setGroupedTransactions(groupedTransactions);
		return new ResponseEntity<>(transactionsDetailsResponseDto, HttpStatus.OK);
	}

	@DeleteMapping("/transactions/{transactionId}")
	public ResponseEntity<ApiResponseDto> deleteTransactionByTransactionId(
			@PathVariable("transactionId") String transactionId) {
		Transaction transaction = transactionService.deleteTransactionByTransactionId(transactionId);
		TransactionDto transactionDto = dataTransferService.mapTransactionToTransactionDto(transaction);
		ApiResponseDto apiResponseDto = ApiResponseDto.generateApiResponse(HttpStatus.OK,
				CustomUtil.getFormattedApiResponseMessage(ApiResponseEnum.TRANSACTION_SUCCESSFULLY_DELETED_WITH_ID,
						transactionId));
		apiResponseDto.setData(Map.of("transaction", transactionDto));
		return new ResponseEntity<ApiResponseDto>(apiResponseDto, HttpStatus.OK);
	}
}
