package com.anshuit.expensemate.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.anshuit.expensemate.entities.Transaction;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

	List<Transaction> findByBookIdAndUserId(String bookId, String userId);

	@Query("{ 'bookId': ?0, 'userId': ?1, 'transactionDate': { $gte: ?2, $lt: ?3 } }")
	List<Transaction> findTransactionsInRange(String bookId, String userId, LocalDateTime start, LocalDateTime end,
			Sort sort);

}
