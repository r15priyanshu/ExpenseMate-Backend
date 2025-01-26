package com.anshuit.expensemate.services.impls;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.anshuit.expensemate.entities.CustomExpenseCategory;
import com.anshuit.expensemate.enums.ExceptionDetailsEnum;
import com.anshuit.expensemate.exceptions.CustomException;
import com.anshuit.expensemate.repositories.CustomExpenseCategoryRepository;

@Service
public class CustomExpenseCategoryServiceImpl {

	@Autowired
	private CustomExpenseCategoryRepository customExpenseCategoryRepository;

	public CustomExpenseCategory saveOrUpdateCustomExpenseCategory(CustomExpenseCategory customExpenseCategory) {
		return customExpenseCategoryRepository.save(customExpenseCategory);
	}

	public Optional<CustomExpenseCategory> getCustomExpenseCategoryByIdOptional(ObjectId customExpenseCategory) {
		return customExpenseCategoryRepository.findById(customExpenseCategory);
	}

	public CustomExpenseCategory getCustomExpenseCategoryById(ObjectId customExpenseCategory) {
		return this.getCustomExpenseCategoryByIdOptional(customExpenseCategory).orElseThrow(() -> {
			throw new CustomException(HttpStatus.NOT_FOUND,
					ExceptionDetailsEnum.CUSTOM_EXPENSE_CATEGORY_NOT_FOUND_WITH_ID, customExpenseCategory);
		});
	}
}
