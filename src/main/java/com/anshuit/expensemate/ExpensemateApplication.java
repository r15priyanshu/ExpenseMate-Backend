package com.anshuit.expensemate;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.anshuit.expensemate.constants.GlobalConstants;
import com.anshuit.expensemate.entities.AppUser;
import com.anshuit.expensemate.entities.Role;
import com.anshuit.expensemate.services.impls.RoleServiceImpl;
import com.anshuit.expensemate.services.impls.UserServiceImpl;

@SpringBootApplication
public class ExpensemateApplication implements ApplicationRunner {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private RoleServiceImpl roleService;

	public static void main(String[] args) {
		SpringApplication.run(ExpensemateApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// CREATING 2 DEFAULT ROLES FOR THIS APPLICATION
		ObjectId roleId1 = new ObjectId(GlobalConstants.DEFAULT_ROLE_ONE_ID);
		ObjectId roleId2 = new ObjectId(GlobalConstants.DEFAULT_ROLE_TWO_ID);
		roleService.getRoleByIdOptional(roleId1)
				.orElseGet(() -> roleService.saveOrUpdateRole(new Role(roleId1, GlobalConstants.DEFAULT_ROLE_ONE)));
		roleService.getRoleByIdOptional(roleId2)
				.orElseGet(() -> roleService.saveOrUpdateRole(new Role(roleId2, GlobalConstants.DEFAULT_ROLE_TWO)));

		// CREATING 2 DEFAULT EMPLOYEES FOR THIS APPLICATION
		userService.getUserByEmailOptional("anshu@gmail.com").orElseGet(() -> {
			AppUser user1 = new AppUser();
			user1.setUserId(new ObjectId(GlobalConstants.DEFAULT_USER_ONE_ID));
			user1.setFirstName("Anshu");
			user1.setLastName("Anand");
			user1.setEmail("anshu@gmail.com");
			user1.setPassword("12345");
			AppUser savedUser1 = userService.createUser(user1, GlobalConstants.DEFAULT_ROLE_ONE_ID);
			return savedUser1;
		});

		userService.getUserByEmailOptional("shalu@gmail.com").orElseGet(() -> {
			AppUser user2 = new AppUser();
			user2.setUserId(new ObjectId(GlobalConstants.DEFAULT_USER_TWO_ID));
			user2.setFirstName("Shalu");
			user2.setLastName("Kumari");
			user2.setEmail("shalu@gmail.com");
			user2.setPassword("12345");
			AppUser savedUser2 = userService.createUser(user2, GlobalConstants.DEFAULT_ROLE_TWO_ID);
			return savedUser2;
		});
	}

}
