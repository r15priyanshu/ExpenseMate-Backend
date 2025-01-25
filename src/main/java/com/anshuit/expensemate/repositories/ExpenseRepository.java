package com.anshuit.expensemate.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.anshuit.expensemate.entities.Expense;

public interface ExpenseRepository extends MongoRepository<Expense, ObjectId> {

}
