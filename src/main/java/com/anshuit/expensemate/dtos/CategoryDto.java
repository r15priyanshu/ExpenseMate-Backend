package com.anshuit.expensemate.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
	private String categoryId;
	private String categoryName;
	private String categoryType;
	private String categoryOwner;
}
