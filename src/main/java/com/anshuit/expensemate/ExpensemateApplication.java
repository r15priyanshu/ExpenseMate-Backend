package com.anshuit.expensemate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.anshuit.expensemate.constants.GlobalConstants;
import com.anshuit.expensemate.entities.Role;
import com.anshuit.expensemate.services.impls.RoleServiceImpl;

@SpringBootApplication
public class ExpensemateApplication implements ApplicationRunner {

	@Autowired
	private RoleServiceImpl roleService;

	public static void main(String[] args) {
		SpringApplication.run(ExpensemateApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// CREATING 2 DEFAULT ROLES FOR THIS APPLICATION
		roleService.getRoleByIdOptional(GlobalConstants.DEFAULT_ROLE_ONE_ID).orElseGet(() -> roleService
				.saveOrUpdateRole(new Role(GlobalConstants.DEFAULT_ROLE_ONE_ID, GlobalConstants.DEFAULT_ROLE_ONE)));
		roleService.getRoleByIdOptional(GlobalConstants.DEFAULT_ROLE_TWO_ID).orElseGet(() -> roleService
				.saveOrUpdateRole(new Role(GlobalConstants.DEFAULT_ROLE_TWO_ID, GlobalConstants.DEFAULT_ROLE_TWO)));
	}

}
