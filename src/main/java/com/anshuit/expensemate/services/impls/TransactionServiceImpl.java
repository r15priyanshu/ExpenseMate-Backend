package com.anshuit.expensemate.services.impls;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.anshuit.expensemate.entities.AppUser;
import com.anshuit.expensemate.entities.Category;
import com.anshuit.expensemate.entities.Transaction;
import com.anshuit.expensemate.enums.ExceptionDetailsEnum;
import com.anshuit.expensemate.exceptions.CustomException;
import com.anshuit.expensemate.repositories.TransactionRepository;

@Service
public class TransactionServiceImpl {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private CategoryServiceImpl categoryService;

	@Autowired
	private MongoTemplate mongoTemplate;

	public Transaction saveOrUpdateTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}

	public Transaction createTransaction(String userId, Transaction transaction, String categoryId) {
		boolean fetchPartial = true;
		AppUser user = userService.getUserByUserId(userId, fetchPartial);
		Category category = categoryService.getCategoryById(categoryId);

		transaction.setTransactionDate(LocalDateTime.now());
		transaction.setTransactionType(category.getCategoryType());
		transaction.setCategory(category);
		transaction.setUserId(user.getUserId());
		Transaction createdTransaction = this.saveOrUpdateTransaction(transaction);

		// Updating the Transaction Created In Expenses List In User As Reference Also.
		Query query = new Query(Criteria.where("_id").is(user.getUserId()));
		Update update = new Update().addToSet("transactions", createdTransaction.getTransactionId());
		mongoTemplate.updateFirst(query, update, AppUser.class);
		return createdTransaction;
	}

	public Optional<Transaction> getTransactionByTransactionIdOptional(String transactionId) {
		return transactionRepository.findById(transactionId);
	}

	public List<Transaction> getAllTransactionsOfUserByUserId(String userId) {
		return transactionRepository.findByUserId(userId);
	}

	public List<Transaction> getSpecificMonthTransactionsOfUserByUserIdInRange(String userId, int year, int month) {
		Sort sort = Sort.by(Direction.DESC, "transactionDate");
		YearMonth yearMonth = YearMonth.of(year, month);
		LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
		LocalDateTime end = yearMonth.plusMonths(1).atDay(1).atStartOfDay();
		return transactionRepository.findTransactionsInRange(userId, start, end, sort);
	}

	public Transaction getTransactionByTransactionId(String transactionId) {
		return this.getTransactionByTransactionIdOptional(transactionId).orElseThrow(() -> {
			throw new CustomException(HttpStatus.NOT_FOUND, ExceptionDetailsEnum.TRANSACTION_NOT_FOUND_WITH_ID,
					transactionId);
		});
	}
}
