package com.anshuit.expensemate.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
	@Id
	private String bookId;
	private String bookName;
	private String bookDescription;
	private String bookOwner;
	private LocalDateTime createdAt;
}
