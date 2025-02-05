package com.anshuit.expensemate.entities;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Category {
	@Id
	private String categoryId;
	private String categoryName;
}
