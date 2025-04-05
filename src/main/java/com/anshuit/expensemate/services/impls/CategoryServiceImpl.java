package com.anshuit.expensemate.services.impls;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.anshuit.expensemate.entities.Category;
import com.anshuit.expensemate.enums.ExceptionDetailsEnum;
import com.anshuit.expensemate.exceptions.CustomException;
import com.anshuit.expensemate.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl {

	@Autowired
	private CategoryRepository categoryRepository;

	public Category saveOrUpdateCategory(Category category) {
		return categoryRepository.save(category);
	}

	public Optional<Category> getCategoryByIdOptional(String categoryId) {
		return categoryRepository.findById(categoryId);
	}

	public Category getCategoryById(String categoryId) {
		Category category = this.getCategoryByIdOptional(categoryId).orElseThrow(() -> {
			throw new CustomException(HttpStatus.NOT_FOUND, ExceptionDetailsEnum.CATEGORY_NOT_FOUND_WITH_ID,
					categoryId);
		});
		return category;
	}
}
