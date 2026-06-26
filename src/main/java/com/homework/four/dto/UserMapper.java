package com.homework.four.dto;

import com.homework.four.entity.User;

public class UserMapper {

	public static UserDtoNameEmail userToDtoNameEmail(User user) {

		UserDtoNameEmail result = new UserDtoNameEmail(user.getId(), user.getName(), user.getEmail());
		return result;
	}

	public static User dtoNameEmailToUser(UserDtoNameEmail user) {
		User result = new User();
		result.setName(user.getName());
		result.setEmail(user.getEmail());
		return result;
	}
	public static UserDtoFull userToDtoFull(User user) {

		UserDtoFull result = new UserDtoFull(user.getId(), user.getName(), user.getEmail(), user.getAge(), user.getCreated_at());
		return result;
	}
	
	public static User dtoFullToUser(UserDtoFull user) {
		User result = new User();
		result.setName(user.getName());
		result.setEmail(user.getEmail());
		result.setAge(user.getAge());
		return result;
	}
}
