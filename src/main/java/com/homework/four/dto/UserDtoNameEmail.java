package com.homework.four.dto;

import lombok.Getter;

@Getter
public class UserDtoNameEmail extends UserDtoName{
	
	private final String email;
	
	public UserDtoNameEmail(Long id, String name, String email) {
		super(id, name);
		this.email = email;
	}
}
