package com.homework.four.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.homework.four.dao.UserDao;
import com.homework.four.dto.UserDtoFull;
import com.homework.four.dto.UserMapper;
import com.homework.four.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserDao repository;

	@Override
	@Transactional
	public UserDtoFull create(UserDtoFull userInput) throws ServiceException {
		log.debug("Создание пользователя {}", userInput.getEmail());
		User userToCreate = UserMapper.dtoFullToUser(userInput);
		userToCreate.setCreated_at(LocalDateTime.now());
		User result = repository.save(userToCreate);
		UserDtoFull userOut = UserMapper.userToDtoFull(result);
		log.info("Пользователь успешно создан: {}", userOut.getEmail());
		return userOut;
	}

	@Override
	public UserDtoFull read(Long id) throws ServiceException {
		log.debug("Получение данных пользователя с ID {}", id);

		Optional<User> OptionalUser = repository.findById(id);

		if (OptionalUser.isPresent()) {
			User user = OptionalUser.get();
			UserDtoFull userOut = UserMapper.userToDtoFull(user);
			log.info("Данные пользователя с ID {} успешно получены", userOut.getId());
			return userOut;
		}

		log.info("Данные пользователя с ID {} не найдены", id);

		throw new ServiceException("Пользователь не найден");
	}

	@Override
	@Transactional
	public UserDtoFull update(Long id, UserDtoFull userInput) {

		log.debug("Обновление данных пользователя: {}", userInput.getEmail());

		User user = repository.findById(id).orElseThrow(() -> new ServiceException("Пользователь не найден"));

		user.setName(userInput.getName());
		user.setEmail(userInput.getEmail());
		user.setAge(userInput.getAge());

		User answer = repository.save(user);

		log.info("Данные пользователя успешно обновлены: {}", answer.getEmail());

		return UserMapper.userToDtoFull(answer);
	}

	@Override
	@Transactional
	public void delete(Long id) throws ServiceException {
		log.debug("Удаление пользователя с ID {}", id);

		if (!repository.existsById(id)) {
			throw new ServiceException("Пользователь не найден");
		}
		repository.deleteById(id);

		log.info("Пользователь успешно удалён");

	}
}
