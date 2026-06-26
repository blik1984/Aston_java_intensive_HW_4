package com.homework.four.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.homework.four.dao.UserDao;
import com.homework.four.dto.UserDtoFull;
import com.homework.four.entity.User;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	@Mock
	private UserDao userDao;
	@InjectMocks
	private UserServiceImpl userService;
	private User testUser;
	private UserDtoFull testDto = new UserDtoFull(null, "Корейко", "111@test.by", 42, null);

	@BeforeEach
	void setUp() {
		testUser = new User();
		testUser.setName("Корейко");
		testUser.setEmail("111@test.by");
		testUser.setAge(42);


	}

	@Nested
	class CreateTest {
		@Test
		void create_ShouldReturnUserDtoFull_whenDaoReturnsUser() {
			when(userDao.save(any(User.class))).thenReturn(testUser);

			UserDtoFull result = userService.create(testDto);

			assertEquals("Корейко", result.getName());
			assertEquals("111@test.by", result.getEmail());
			verify(userDao, times(1)).save(any(User.class));
		}
	}

	@Nested
	class ReadTest {
		@Test
		void read_ShouldReturnUserDtoFull_WhenDaoReturnsUser() throws ServiceException {

			when(userDao.findById(1L)).thenReturn(Optional.of(testUser));

			UserDtoFull user = userService.read(1L);

			assertEquals(testUser.getName(), user.getName());
			assertEquals(testUser.getEmail(), user.getEmail());

			verify(userDao, times(1)).findById(1L);
		}

		@Test
		void read_ShouldThrowServiceException_WhenUserNotFound() {
			when(userDao.findById(1L)).thenReturn(Optional.empty());

			ServiceException exception = assertThrows(ServiceException.class, () -> userService.read(1L));

			assertEquals("Пользователь не найден", exception.getMessage());
			verify(userDao, times(1)).findById(1L);
		}
	}

	@Nested
	class UpdateTest {
		@Test
		void update_ShouldReturnUserDtoFull_whenDaoReturnsUser() throws ServiceException {
			when(userDao.findById(1L)).thenReturn(Optional.of(testUser));
			when(userDao.save(any(User.class))).thenReturn(testUser);

			UserDtoFull updatedTestDto = new UserDtoFull(1L, "Жулик", "222@test.by", 33, testDto.getCreatedAt());
			UserDtoFull result = userService.update(1L, updatedTestDto);

			assertEquals("Жулик", result.getName());
			assertEquals("222@test.by", result.getEmail());
			assertEquals(33, result.getAge());
			verify(userDao, times(1)).save(any(User.class));
		}

		@Test
		void update_ShouldThrowServiceException_WhenUserNotFound() {
			when(userDao.findById(1L)).thenReturn(Optional.empty());

			ServiceException exception = assertThrows(ServiceException.class, () -> userService.update(1L, testDto));

			assertEquals("Пользователь не найден", exception.getMessage());
			verify(userDao, times(1)).findById(1L);
		}
	}

	@Nested
	class DeleteTest {
		@Test
		void delete_ShouldDeleteUser() throws ServiceException {
			
			when(userDao.existsById(1L)).thenReturn(true);
			
			userService.delete(1L);

			verify(userDao, times(1)).deleteById(1L);
		}

		@Test
		void delete_ShouldThrowServiceException_WhenUserNotFound() {
			when(userDao.existsById(1L)).thenReturn(false);

			ServiceException exception = assertThrows(ServiceException.class, () -> userService.delete(1L));

			assertEquals("Пользователь не найден", exception.getMessage());
			verify(userDao, times(1)).existsById(1L);
			verify(userDao, never()).deleteById(anyLong());
		}
	}
}
