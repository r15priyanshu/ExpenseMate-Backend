package com.anshuit.expensemate.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {

	private static final long serialVersionUID = 3391150225279776406L;

	@Id
	private String roleId;
	private String roleName;

	public Role(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String getAuthority() {
		return this.roleName;
	}
}
