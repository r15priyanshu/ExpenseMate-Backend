package com.anshuit.expensemate.services.impls;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.anshuit.expensemate.entities.AppUser;
import com.anshuit.expensemate.entities.Category;
import com.anshuit.expensemate.enums.ExceptionDetailsEnum;
import com.anshuit.expensemate.exceptions.CustomException;
import com.anshuit.expensemate.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private CategoryRepository categoryRepository;

	public Category saveOrUpdateCategory(Category category) {
		return categoryRepository.save(category);
	}

	public Category createCategoryForSpecificUser(String userId, Category category) {
		AppUser foundUser = userService.getUserByUserId(userId, true);
		category.setCategoryOwner(foundUser.getUserId());
		category.setCreatedAt(LocalDateTime.now());
		return this.saveOrUpdateCategory(category);
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

	public List<Category> getCategoriesByUserIdOrSystem(String userId) {
		List<Category> categories = this.categoryRepository.findCategoryByCategoryOwnerOrSystem(userId);
		return categories;
	}
}
