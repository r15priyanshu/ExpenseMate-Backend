package com.anshuit.expensemate.entities;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Category {
	@Id
	private ObjectId categoryId;
	private String categoryName;
}
