package com.anshuit.expensemate.services.impls;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anshuit.expensemate.dtos.AppUserDto;
import com.anshuit.expensemate.dtos.BookDto;
import com.anshuit.expensemate.dtos.CategoryDto;
import com.anshuit.expensemate.dtos.TransactionDto;
import com.anshuit.expensemate.entities.AppUser;
import com.anshuit.expensemate.entities.Book;
import com.anshuit.expensemate.entities.Category;
import com.anshuit.expensemate.entities.Transaction;

@Service
public class DataTransferServiceImpl {

	@Autowired
	private ModelMapper modelMapper;

	public AppUserDto mapUserToUserDto(AppUser appUser) {
		return modelMapper.map(appUser, AppUserDto.class);
	}

	public AppUser mapUserDtoToUser(AppUserDto appUserDto) {
		return modelMapper.map(appUserDto, AppUser.class);
	}

	public TransactionDto mapTransactionToTransactionDto(Transaction expense) {
		return modelMapper.map(expense, TransactionDto.class);
	}

	public Transaction mapTransactionDtoToTransaction(TransactionDto expenseDto) {
		return modelMapper.map(expenseDto, Transaction.class);
	}

	public CategoryDto mapCategoryToCategoryDto(Category category) {
		return modelMapper.map(category, CategoryDto.class);
	}

	public Category mapCategoryDtoToCategory(CategoryDto categoryDto) {
		return modelMapper.map(categoryDto, Category.class);
	}

	public BookDto mapBookToBookDto(Book book) {
		return modelMapper.map(book, BookDto.class);
	}

	public Book mapBookDtoToBook(BookDto bookDto) {
		return modelMapper.map(bookDto, Book.class);
	}
}
