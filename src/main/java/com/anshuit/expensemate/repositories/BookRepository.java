package com.anshuit.expensemate.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anshuit.expensemate.entities.Book;

public interface BookRepository extends MongoRepository<Book, String> {
	List<Book> findByBookOwner(String userId);
}
