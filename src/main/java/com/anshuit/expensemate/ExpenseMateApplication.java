package com.anshuit.expensemate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.anshuit.expensemate.constants.GlobalConstants;
import com.anshuit.expensemate.entities.AppUser;
import com.anshuit.expensemate.entities.CustomExpenseCategory;
import com.anshuit.expensemate.entities.DefaultExpenseCategory;
import com.anshuit.expensemate.entities.Role;
import com.anshuit.expensemate.services.impls.CustomExpenseCategoryServiceImpl;
import com.anshuit.expensemate.services.impls.DefaultExpenseCategoryServiceImpl;
import com.anshuit.expensemate.services.impls.RoleServiceImpl;
import com.anshuit.expensemate.services.impls.UserServiceImpl;

@SpringBootApplication
public class ExpenseMateApplication implements ApplicationRunner {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private RoleServiceImpl roleService;

	@Autowired
	private DefaultExpenseCategoryServiceImpl defaultExpenseCategoryService;

	@Autowired
	private CustomExpenseCategoryServiceImpl customExpenseCategoryService;

	public static void main(String[] args) {
		SpringApplication.run(ExpenseMateApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// CREATING 2 DEFAULT ROLES FOR THIS APPLICATION
		String roleId1 = GlobalConstants.DEFAULT_ROLE_ONE_ID;
		String roleId2 = GlobalConstants.DEFAULT_ROLE_TWO_ID;
		roleService.getRoleByIdOptional(roleId1)
				.orElseGet(() -> roleService.saveOrUpdateRole(new Role(roleId1, GlobalConstants.DEFAULT_ROLE_ONE)));
		roleService.getRoleByIdOptional(roleId2)
				.orElseGet(() -> roleService.saveOrUpdateRole(new Role(roleId2, GlobalConstants.DEFAULT_ROLE_TWO)));

		// CREATING 2 DEFAULT CATEGORIES FOR THIS APPLICATION
		String defaultCategoryId1 = GlobalConstants.DEFAULT_CATEGORY_ONE_ID;
		defaultExpenseCategoryService.getDefaultExpenseCategoryByIdOptional(defaultCategoryId1).orElseGet(() -> {
			DefaultExpenseCategory category = new DefaultExpenseCategory();
			category.setCategoryId(defaultCategoryId1);
			category.setCategoryName(GlobalConstants.DEFAULT_CATEGORY_ONE);
			return defaultExpenseCategoryService.saveOrUpdateDefaultExpenseCategory(category);
		});
		String defaultCategoryId2 = GlobalConstants.DEFAULT_CATEGORY_TWO_ID;
		defaultExpenseCategoryService.getDefaultExpenseCategoryByIdOptional(defaultCategoryId2).orElseGet(() -> {
			DefaultExpenseCategory category = new DefaultExpenseCategory();
			category.setCategoryId(defaultCategoryId2);
			category.setCategoryName(GlobalConstants.DEFAULT_CATEGORY_TWO);
			return defaultExpenseCategoryService.saveOrUpdateDefaultExpenseCategory(category);
		});

		// CREATING 2 CUSTOM CATEGORIES FOR SOME USERS
		String customCategoryId1 = GlobalConstants.CUSTOM_CATEGORY_ONE_ID;
		CustomExpenseCategory customExpenseCategory1 = customExpenseCategoryService
				.getCustomExpenseCategoryByIdOptional(customCategoryId1).orElseGet(() -> {
					CustomExpenseCategory category = new CustomExpenseCategory();
					category.setCategoryId(customCategoryId1);
					category.setCategoryName(GlobalConstants.CUSTOM_CATEGORY_ONE);
					return customExpenseCategoryService.saveOrUpdateCustomExpenseCategory(category);
				});
		String customCategoryId2 = GlobalConstants.CUSTOM_CATEGORY_TWO_ID;
		CustomExpenseCategory customExpenseCategory2 = customExpenseCategoryService
				.getCustomExpenseCategoryByIdOptional(customCategoryId2).orElseGet(() -> {
					CustomExpenseCategory category = new CustomExpenseCategory();
					category.setCategoryId(customCategoryId2);
					category.setCategoryName(GlobalConstants.CUSTOM_CATEGORY_TWO);
					return customExpenseCategoryService.saveOrUpdateCustomExpenseCategory(category);
				});

		// CREATING 2 DEFAULT EMPLOYEES FOR THIS APPLICATION
		userService.getUserByEmailOptional("anshu@gmail.com").orElseGet(() -> {
			AppUser user1 = new AppUser();
			user1.setUserId(GlobalConstants.DEFAULT_USER_ONE_ID);
			user1.setFirstName("Anshu");
			user1.setLastName("Anand");
			user1.setEmail("anshu@gmail.com");
			user1.setPassword("12345");
			AppUser savedUser1 = userService.createUser(user1, GlobalConstants.DEFAULT_ROLE_ONE_ID);
			savedUser1.getCustomExpenseCategories().addAll(List.of(customExpenseCategory1, customExpenseCategory2));
			savedUser1 = userService.saveOrUpdateUser(savedUser1);
			return savedUser1;
		});

		userService.getUserByEmailOptional("shalu@gmail.com").orElseGet(() -> {
			AppUser user2 = new AppUser();
			user2.setUserId(GlobalConstants.DEFAULT_USER_TWO_ID);
			user2.setFirstName("Shalu");
			user2.setLastName("Kumari");
			user2.setEmail("shalu@gmail.com");
			user2.setPassword("12345");
			AppUser savedUser2 = userService.createUser(user2, GlobalConstants.DEFAULT_ROLE_TWO_ID);
			return savedUser2;
		});

	}
}
