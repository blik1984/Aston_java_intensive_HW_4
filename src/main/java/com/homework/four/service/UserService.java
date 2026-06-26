package com.homework.four.service;

import com.homework.four.dto.UserDtoFull;

public interface UserService {
	
	UserDtoFull create(UserDtoFull user) throws ServiceException;
	UserDtoFull read (Long id) throws ServiceException;
	UserDtoFull update (Long id, UserDtoFull user) throws ServiceException;
	void delete (Long id) throws ServiceException;

}
