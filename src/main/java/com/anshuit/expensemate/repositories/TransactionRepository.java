package com.anshuit.expensemate.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anshuit.expensemate.entities.Transaction;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

	List<Transaction> findByUserId(String userId);

}
