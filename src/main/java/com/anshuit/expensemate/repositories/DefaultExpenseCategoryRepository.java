package com.anshuit.expensemate.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.anshuit.expensemate.entities.DefaultExpenseCategory;

public interface DefaultExpenseCategoryRepository extends MongoRepository<DefaultExpenseCategory, ObjectId> {

}
