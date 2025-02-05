package com.anshuit.expensemate.services.impls;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.anshuit.expensemate.entities.DefaultExpenseCategory;
import com.anshuit.expensemate.enums.ExceptionDetailsEnum;
import com.anshuit.expensemate.exceptions.CustomException;
import com.anshuit.expensemate.repositories.DefaultExpenseCategoryRepository;

@Service
public class DefaultExpenseCategoryServiceImpl {

	@Autowired
	private DefaultExpenseCategoryRepository defaultExpenseCategoryRepository;

	public DefaultExpenseCategory saveOrUpdateDefaultExpenseCategory(DefaultExpenseCategory defaultExpenseCategory) {
		return defaultExpenseCategoryRepository.save(defaultExpenseCategory);
	}

	public Optional<DefaultExpenseCategory> getDefaultExpenseCategoryByIdOptional(String defaultExpenseCategoryId) {
		return defaultExpenseCategoryRepository.findById(defaultExpenseCategoryId);
	}

	public DefaultExpenseCategory getDefaultExpenseCategoryById(String defaultExpenseCategoryId) {
		return this.getDefaultExpenseCategoryByIdOptional(defaultExpenseCategoryId).orElseThrow(() -> {
			throw new CustomException(HttpStatus.NOT_FOUND, ExceptionDetailsEnum.DEFAULT_EXPENSE_CATEGORY_NOT_FOUND_WITH_ID,
					defaultExpenseCategoryId);
		});
	}
}
