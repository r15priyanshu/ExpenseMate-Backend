package com.anshuit.expensemate.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anshuit.expensemate.dtos.BookDto;
import com.anshuit.expensemate.entities.Book;
import com.anshuit.expensemate.services.impls.BookServiceImpl;
import com.anshuit.expensemate.services.impls.DataTransferServiceImpl;

@RestController
public class BookController {

	@Autowired
	private BookServiceImpl bookService;

	@Autowired
	private DataTransferServiceImpl dataTransferService;

	@PostMapping("/books/users/{userId}")
	public ResponseEntity<BookDto> createBookForSpecificUser(@PathVariable("userId") String userId,
			@RequestBody BookDto bookDto) {
		Book book = dataTransferService.mapBookDtoToBook(bookDto);
		Book createdBook = bookService.createBookForSpecificUser(userId, book);
		BookDto createdBookDto = dataTransferService.mapBookToBookDto(createdBook);
		return new ResponseEntity<BookDto>(createdBookDto, HttpStatus.CREATED);
	}

	@GetMapping("/books/users/{userId}")
	public List<BookDto> getBooksByUserId(@PathVariable("userId") String userId) {
		List<Book> books = bookService.getBooksByUserId(userId);
		List<BookDto> bookDtos = books.stream().map(book -> dataTransferService.mapBookToBookDto(book)).toList();
		return bookDtos;
	}
}
