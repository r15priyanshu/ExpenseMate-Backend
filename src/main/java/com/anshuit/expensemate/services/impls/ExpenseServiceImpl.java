package com.anshuit.expensemate.services.impls;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.anshuit.expensemate.entities.AppUser;
import com.anshuit.expensemate.entities.Expense;
import com.anshuit.expensemate.enums.ExceptionDetailsEnum;
import com.anshuit.expensemate.exceptions.CustomException;
import com.anshuit.expensemate.repositories.ExpenseRepository;

@Service
public class ExpenseServiceImpl {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private ExpenseRepository expenseRepository;

	public Expense saveOrUpdateExpense(Expense expense) {
		return expenseRepository.save(expense);
	}

	public Expense createExpense(ObjectId userId, Expense expense) {
		AppUser user = userService.getUserByUserId(userId);
		Expense createdExpense = this.saveOrUpdateExpense(expense);
		return createdExpense;
	}

	public Optional<Expense> getExpenseByExpenseIdOptional(ObjectId expenseId) {
		return expenseRepository.findById(expenseId);
	}

	public Expense getExpenseByExpenseId(ObjectId expenseId) {
		return this.getExpenseByExpenseIdOptional(expenseId).orElseThrow(() -> {
			throw new CustomException(HttpStatus.NOT_FOUND, ExceptionDetailsEnum.EXPENSE_NOT_FOUND_WITH_ID, expenseId);
		});
	}
}
