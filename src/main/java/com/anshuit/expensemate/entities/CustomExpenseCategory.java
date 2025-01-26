package com.anshuit.expensemate.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "custom_expenses_categories")
@Getter
@Setter
@NoArgsConstructor
public class CustomExpenseCategory extends Category {

}
