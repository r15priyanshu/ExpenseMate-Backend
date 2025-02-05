package com.anshuit.expensemate.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anshuit.expensemate.entities.CustomExpenseCategory;

public interface CustomExpenseCategoryRepository extends MongoRepository<CustomExpenseCategory, String> {

}
