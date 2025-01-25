package com.anshuit.expensemate.services.impls;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.anshuit.expensemate.entities.Role;
import com.anshuit.expensemate.enums.ExceptionDetailsEnum;
import com.anshuit.expensemate.exceptions.CustomException;
import com.anshuit.expensemate.repositories.RoleRepository;

@Service
public class RoleServiceImpl {

	@Autowired
	private RoleRepository roleRepository;

	public Role saveOrUpdateRole(Role role) {
		return roleRepository.save(role);
	}

	public Optional<Role> getRoleByIdOptional(ObjectId roleId) {
		return roleRepository.findById(roleId);
	}

	public Role getRoleById(ObjectId roleId) {
		return this.getRoleByIdOptional(roleId).orElseThrow(() -> {
			throw new CustomException(HttpStatus.NOT_FOUND, ExceptionDetailsEnum.ROLE_NOT_FOUND_WITH_ID, roleId);
		});
	}
}
