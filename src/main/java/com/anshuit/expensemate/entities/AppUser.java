package com.anshuit.expensemate.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
public class AppUser implements UserDetails {

	private static final long serialVersionUID = -3173463497851204487L;

	@Id
	private String userId;
	private String firstName;
	private String lastName;

	@Indexed(unique = true)
	private String email;
	private String password;
	private String profilePic;
	private byte[] profilePicData;

	@DBRef
	private Role role;

	@DBRef(lazy = true)
	private List<Transaction> transactions = new ArrayList<>();

	@DBRef
	private List<Book> books = new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(this.role.getRoleName()));
	}

	@Override
	public String getUsername() {
		return this.email;
	}
}
