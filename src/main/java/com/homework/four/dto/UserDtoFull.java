package com.homework.four.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class UserDtoFull extends UserDtoNameEmail{
	
	private final int age;
	private final LocalDateTime createdAt;
	
	public UserDtoFull (Long id, String name, String email, int age, LocalDateTime createdAt) {
		super(id, name, email);
		this.age = age;
		this.createdAt = createdAt;
	}
}
