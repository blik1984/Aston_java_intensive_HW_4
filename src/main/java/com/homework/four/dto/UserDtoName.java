package com.homework.four.dto;

import lombok.Getter;

@Getter
public class UserDtoName {
	private final Long id;
	private final String name;
	
	public UserDtoName(String name) {
		this.id = null;
		this.name = name;
	}
	public UserDtoName(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
