package com.anshuit.expensemate.services.impls;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.anshuit.expensemate.entities.AppUser;
import com.anshuit.expensemate.entities.Book;
import com.anshuit.expensemate.enums.ExceptionDetailsEnum;
import com.anshuit.expensemate.exceptions.CustomException;
import com.anshuit.expensemate.repositories.BookRepository;

@Service
public class BookServiceImpl {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	public Book saveOrUpdateBook(Book book) {
		return bookRepository.save(book);
	}

	public Book createBookForSpecificUser(String userId, Book book) {
		AppUser foundUser = userService.getUserByUserId(userId, true);
		book.setBookOwner(foundUser.getUserId());
		book.setCreatedAt(LocalDateTime.now());
		Book createdBook = this.saveOrUpdateBook(book);

		// Updating the Book Created In Books List In User As Reference Also.
		Query query = new Query(Criteria.where("_id").is(foundUser.getUserId()));
		Update update = new Update().addToSet("books", createdBook.getBookId());
		mongoTemplate.updateFirst(query, update, AppUser.class);

		return createdBook;
	}

	public Optional<Book> getBookByIdOptional(String bookId) {
		return bookRepository.findById(bookId);
	}

	public Book getBookById(String bookId) {
		Book book = this.getBookByIdOptional(bookId).orElseThrow(() -> {
			throw new CustomException(HttpStatus.NOT_FOUND, ExceptionDetailsEnum.BOOK_NOT_FOUND_WITH_ID, bookId);
		});
		return book;
	}

	public List<Book> getBooksByUserId(String userId) {
		List<Book> books = this.bookRepository.findByBookOwner(userId);
		return books;
	}
}
