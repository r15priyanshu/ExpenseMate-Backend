package com.anshuit.expensemate.services.impls;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anshuit.expensemate.dtos.AppUserDto;
import com.anshuit.expensemate.entities.AppUser;

@Service
public class DataTransferServiceImpl {

	@Autowired
	private ModelMapper modelMapper;

	public AppUserDto mapUserToUserDto(AppUser appUser) {
		return modelMapper.map(appUser, AppUserDto.class);
	}

	public AppUser mapUserDtoToUser(AppUserDto appUserDto) {
		return modelMapper.map(appUserDto, AppUser.class);
	}
}
