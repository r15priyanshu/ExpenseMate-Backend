package com.anshuit.expensemate.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

	private ModelMapper modelMapper;

	@Bean
	ModelMapper getModelMapper() {
		this.modelMapper = new ModelMapper();
		return this.modelMapper;
	}
}
