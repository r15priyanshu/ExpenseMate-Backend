package com.anshuit.expensemate.services.impls;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.anshuit.expensemate.entities.AppUser;
import com.anshuit.expensemate.entities.Category;
import com.anshuit.expensemate.entities.DefaultExpenseCategory;
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

	@Autowired
	private DefaultExpenseCategoryServiceImpl defaultExpenseCategoryService;

	@Autowired
	private MongoTemplate mongoTemplate;

	public Expense saveOrUpdateExpense(Expense expense) {
		return expenseRepository.save(expense);
	}

	public Expense createExpense(ObjectId userId, Expense expense, ObjectId categoryId) {
		AppUser user = userService.getUserByUserId(userId);

		Optional<DefaultExpenseCategory> defaultCategory = defaultExpenseCategoryService
				.getDefaultExpenseCategoryByIdOptional(categoryId);
		if (defaultCategory.isPresent()) {
			expense.setCategory(defaultCategory.get());
		} else {
			Optional<Category> customCategory = user.getCustomExpenseCategories().stream()
					.filter(category -> category.getCategoryId().equals(categoryId)).findFirst();
			if (customCategory.isPresent()) {
				expense.setCategory(customCategory.get());
			} else {
				throw new CustomException(HttpStatus.BAD_REQUEST, ExceptionDetailsEnum.CATEGORY_NOT_FOUND_WITH_ID,
						categoryId);
			}
		}
		Expense createdExpense = this.saveOrUpdateExpense(expense);

		// Updating the User Expenses List Also
		Query query = new Query(Criteria.where("_id").is(user.getUserId()));
		Update update = new Update().addToSet("expenses", createdExpense.getExpenseId());
		mongoTemplate.updateFirst(query, update, AppUser.class);
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
