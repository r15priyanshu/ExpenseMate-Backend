package com.anshuit.expensemate.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "categories")
@Getter
@Setter
public class Category {
	@Id
	private String categoryId;
	private String categoryName;
	private String categoryType;
	private String categoryOwner;
}
