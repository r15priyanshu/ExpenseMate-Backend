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

import com.anshuit.expensemate.dtos.ExpenseDto;
import com.anshuit.expensemate.entities.Expense;
import com.anshuit.expensemate.services.impls.DataTransferServiceImpl;
import com.anshuit.expensemate.services.impls.ExpenseServiceImpl;

@RestController
public class ExpenseController {

	@Autowired
	private ExpenseServiceImpl expenseService;

	@Autowired
	private DataTransferServiceImpl dataTransferService;

	@PostMapping("/expenses/users/{userId}/category/{categoryId}")
	public ResponseEntity<ExpenseDto> createExpense(@PathVariable("userId") String userId,
			@PathVariable("categoryId") String categoryId, @RequestBody ExpenseDto expenseDto) {
		Expense createdExpense = expenseService.createExpense(userId,
				dataTransferService.mapExpenseDtoToExpense(expenseDto), categoryId);
		ExpenseDto createdExpenseDto = dataTransferService.mapExpenseToExpenseDto(createdExpense);
		return new ResponseEntity<>(createdExpenseDto, HttpStatus.CREATED);
	}

	@GetMapping("/expenses/{expenseId}")
	public ResponseEntity<ExpenseDto> getExpenseByExpenseId(@PathVariable("expenseId") String expenseId) {
		Expense expense = expenseService.getExpenseByExpenseId(expenseId);
		ExpenseDto expenseDto = dataTransferService.mapExpenseToExpenseDto(expense);
		return new ResponseEntity<>(expenseDto, HttpStatus.OK);
	}

	@GetMapping("/expenses/users/{userId}")
	public ResponseEntity<List<ExpenseDto>> getAllExpensesOfUserByUserId(@PathVariable("userId") String userId) {
		List<Expense> allExpenses = expenseService.getAllExpensesOfUserByUserId(userId);
		List<ExpenseDto> allExpensesDto = allExpenses.stream()
				.map(expense -> dataTransferService.mapExpenseToExpenseDto(expense)).toList();
		return new ResponseEntity<>(allExpensesDto, HttpStatus.OK);
	}
}
