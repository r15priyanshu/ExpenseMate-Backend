package com.anshuit.expensemate.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.anshuit.expensemate.entities.AppUser;
import com.anshuit.expensemate.enums.ExceptionDetailsEnum;
import com.anshuit.expensemate.repositories.UserRepository;
import com.anshuit.expensemate.utils.CustomUtil;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = userRepository.findByEmailPartial(username).orElseThrow(() -> {
			throw new UsernameNotFoundException(CustomUtil.getFormattedExceptionMessage(ExceptionDetailsEnum.USER_NOT_FOUND_WITH_EMAIL, username));
		});
		return user;
	}
}
