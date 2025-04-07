package com.anshuit.expensemate.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.anshuit.expensemate.entities.Transaction;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

	List<Transaction> findByUserId(String userId);

	@Query("{ 'userId': ?0, 'transactionDate': { $gte: ?1, $lt: ?2 } }")
	List<Transaction> findTransactionsInRange(String userId, LocalDateTime start, LocalDateTime end, Sort sort);

}
