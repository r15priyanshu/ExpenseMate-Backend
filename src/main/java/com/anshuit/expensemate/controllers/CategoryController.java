package com.anshuit.expensemate.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anshuit.expensemate.dtos.CategoryDto;
import com.anshuit.expensemate.entities.Category;
import com.anshuit.expensemate.services.impls.CategoryServiceImpl;
import com.anshuit.expensemate.services.impls.DataTransferServiceImpl;

@RestController
public class CategoryController {

	@Autowired
	private CategoryServiceImpl categoryService;

	@Autowired
	private DataTransferServiceImpl dataTransferService;

	@PostMapping("/categories/users/{userId}")
	public CategoryDto createCategoryForSpecificUser(@PathVariable("userId") String userId,
			@RequestBody CategoryDto categoryDto) {
		Category category = dataTransferService.mapCategoryDtoToCategory(categoryDto);
		Category createdCategory = categoryService.createCategoryForSpecificUser(userId, category);
		CategoryDto createdCategoryDto = dataTransferService.mapCategoryToCategoryDto(createdCategory);
		return createdCategoryDto;
	}

	@GetMapping("/categories/users/{userId}")
	public List<CategoryDto> getCategoriesByUserIdOrSystem(@PathVariable("userId") String userId) {

		List<Category> categories = categoryService.getCategoriesByUserIdOrSystem(userId);
		List<CategoryDto> categoriesDto = categories.stream()
				.map(category -> dataTransferService.mapCategoryToCategoryDto(category)).toList();
		return categoriesDto;
	}
}
