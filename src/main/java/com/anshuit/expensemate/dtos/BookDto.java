package com.anshuit.expensemate.dtos;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
	private String bookId;
	private String bookName;
	private String bookDescription;
	private String bookOwner;
	private boolean primary;
	private LocalDateTime createdAt;
}
