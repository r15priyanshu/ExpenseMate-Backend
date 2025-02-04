package com.anshuit.expensemate.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {
	private String roleId;
	private String roleName;

	@JsonIgnore
	public String getAuthority() {
		return this.roleName;
	}
}
