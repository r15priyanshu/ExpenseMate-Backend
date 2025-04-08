package com.anshuit.expensemate;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.anshuit.expensemate.constants.GlobalConstants;
import com.anshuit.expensemate.entities.AppUser;
import com.anshuit.expensemate.entities.Book;
import com.anshuit.expensemate.entities.Category;
import com.anshuit.expensemate.entities.Role;
import com.anshuit.expensemate.services.impls.BookServiceImpl;
import com.anshuit.expensemate.services.impls.CategoryServiceImpl;
import com.anshuit.expensemate.services.impls.RoleServiceImpl;
import com.anshuit.expensemate.services.impls.UserServiceImpl;

@SpringBootApplication
public class ExpenseMateApplication implements ApplicationRunner {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private RoleServiceImpl roleService;

	@Autowired
	private CategoryServiceImpl categoryService;

	@Autowired
	private BookServiceImpl bookService;

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

		// CREATING 2 DEFAULT EMPLOYEES FOR THIS APPLICATION
		AppUser appUser1 = userService.getUserByEmailOptional("anshu@gmail.com").orElseGet(() -> {
			AppUser user1 = new AppUser();
			user1.setUserId(GlobalConstants.DEFAULT_USER_ONE_ID);
			user1.setFirstName("Anshu");
			user1.setLastName("Anand");
			user1.setEmail("anshu@gmail.com");
			user1.setPassword("12345");
			AppUser savedUser1 = userService.createUser(user1, GlobalConstants.DEFAULT_ROLE_ONE_ID);
			return savedUser1;
		});

		AppUser appUser2 = userService.getUserByEmailOptional("shalu@gmail.com").orElseGet(() -> {
			AppUser user2 = new AppUser();
			user2.setUserId(GlobalConstants.DEFAULT_USER_TWO_ID);
			user2.setFirstName("Shalu");
			user2.setLastName("Kumari");
			user2.setEmail("shalu@gmail.com");
			user2.setPassword("12345");
			AppUser savedUser2 = userService.createUser(user2, GlobalConstants.DEFAULT_ROLE_TWO_ID);
			return savedUser2;
		});

		// CREATING 2 SYSTEM DEFAULT CATEGORIES FOR THIS APPLICATION
		String defaultCategoryId1 = GlobalConstants.DEFAULT_CATEGORY_ONE_ID;
		categoryService.getCategoryByIdOptional(defaultCategoryId1).orElseGet(() -> {
			Category category = new Category();
			category.setCategoryId(defaultCategoryId1);
			category.setCategoryName(GlobalConstants.DEFAULT_CATEGORY_ONE);
			category.setCategoryType(GlobalConstants.CATEGORY_TYPE_CREDIT);
			category.setCategoryOwner(GlobalConstants.CATEGORY_OWNER_SYSTEM);
			category.setCreatedAt(LocalDateTime.now());
			return categoryService.saveOrUpdateCategory(category);
		});

		String defaultCategoryId2 = GlobalConstants.DEFAULT_CATEGORY_TWO_ID;
		categoryService.getCategoryByIdOptional(defaultCategoryId2).orElseGet(() -> {
			Category category = new Category();
			category.setCategoryId(defaultCategoryId2);
			category.setCategoryName(GlobalConstants.DEFAULT_CATEGORY_TWO);
			category.setCategoryType(GlobalConstants.CATEGORY_TYPE_DEBIT);
			category.setCategoryOwner(GlobalConstants.CATEGORY_OWNER_SYSTEM);
			category.setCreatedAt(LocalDateTime.now());
			return categoryService.saveOrUpdateCategory(category);
		});

		// CREATING 2 CUSTOM CATEGORIES BY SOME USERS
		String customCategoryId1 = GlobalConstants.CUSTOM_CATEGORY_ONE_ID;
		categoryService.getCategoryByIdOptional(customCategoryId1).orElseGet(() -> {
			Category category = new Category();
			category.setCategoryId(customCategoryId1);
			category.setCategoryName(GlobalConstants.CUSTOM_CATEGORY_ONE);
			category.setCategoryType(GlobalConstants.CATEGORY_TYPE_DEBIT);
			category.setCategoryOwner(appUser1.getUserId());
			category.setCreatedAt(LocalDateTime.now());
			return categoryService.saveOrUpdateCategory(category);
		});

		String customCategoryId2 = GlobalConstants.CUSTOM_CATEGORY_TWO_ID;
		categoryService.getCategoryByIdOptional(customCategoryId2).orElseGet(() -> {
			Category category = new Category();
			category.setCategoryId(customCategoryId2);
			category.setCategoryName(GlobalConstants.CUSTOM_CATEGORY_TWO);
			category.setCategoryType(GlobalConstants.CATEGORY_TYPE_DEBIT);
			category.setCategoryOwner(appUser2.getUserId());
			category.setCreatedAt(LocalDateTime.now());
			return categoryService.saveOrUpdateCategory(category);
		});

		// CREATING 2 CUSTOM BOOKS FOR SOME USERS
		String customBookId1 = GlobalConstants.CUSTOM_BOOK_ONE_ID;
		bookService.getBookByIdOptional(customBookId1).orElseGet(() -> {
			Book book = new Book();
			book.setBookId(customBookId1);
			book.setBookName(GlobalConstants.CUSTOM_BOOK_ONE);
			book.setBookDescription("THIS BOOK IS CREATED FOR TESTING PURPOSE.");
			return bookService.createBookForSpecificUser(appUser1.getUserId(), book);
		});

		String customBookId2 = GlobalConstants.CUSTOM_BOOK_TWO_ID;
		bookService.getBookByIdOptional(customBookId2).orElseGet(() -> {
			Book book = new Book();
			book.setBookId(customBookId2);
			book.setBookName(GlobalConstants.CUSTOM_BOOK_TWO);
			book.setBookDescription("THIS BOOK IS CREATED FOR TESTING PURPOSE.");
			return bookService.createBookForSpecificUser(appUser1.getUserId(), book);
		});

	}
}
