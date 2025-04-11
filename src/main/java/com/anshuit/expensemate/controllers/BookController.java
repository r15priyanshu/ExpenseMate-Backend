package com.anshuit.expensemate.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anshuit.expensemate.dtos.ApiResponseDto;
import com.anshuit.expensemate.dtos.BookDto;
import com.anshuit.expensemate.entities.Book;
import com.anshuit.expensemate.enums.ApiResponseEnum;
import com.anshuit.expensemate.services.impls.BookServiceImpl;
import com.anshuit.expensemate.services.impls.DataTransferServiceImpl;
import com.anshuit.expensemate.utils.CustomUtil;

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

	@PutMapping("/books/{bookId}/users/{userId}/makePrimary")
	public ResponseEntity<ApiResponseDto> makeBookPrimary(@PathVariable("bookId") String bookId,
			@PathVariable("userId") String userId) {
		Book book = bookService.makeBookPrimary(userId, bookId);
		BookDto bookDto = dataTransferService.mapBookToBookDto(book);
		ApiResponseDto apiResponseDto = ApiResponseDto.generateApiResponse(HttpStatus.OK,
				CustomUtil.getFormattedApiResponseMessage(ApiResponseEnum.BOOK_WITH_ID_IS_NOW_PRIMARY, bookId));
		apiResponseDto.setData(Map.of("book", bookDto));
		return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
	}

	@GetMapping("/books/users/{userId}")
	public ResponseEntity<List<BookDto>> getBooksByUserId(@PathVariable("userId") String userId) {
		List<Book> books = bookService.getBooksByUserId(userId);
		List<BookDto> booksDtos = books.stream().map(book -> dataTransferService.mapBookToBookDto(book)).toList();
		return new ResponseEntity<>(booksDtos, HttpStatus.OK);
	}
}
