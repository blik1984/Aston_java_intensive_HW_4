package com.homework.four.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homework.four.dto.UserDtoFull;
import com.homework.four.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;
	@MockitoBean
	private UserService service;

	@Test
	void create_shouldReturnUser() throws Exception {

		UserDtoFull request = new UserDtoFull(null, "blik", "blik@test.com", 42, null);

		UserDtoFull response = new UserDtoFull(1L, "blik", "blik@test.com", 42, LocalDateTime.now());

		when(service.create(any(UserDtoFull.class))).thenReturn(response);

		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.name").value("blik"))
				.andExpect(jsonPath("$.email").value("blik@test.com")).andExpect(jsonPath("$.age").value(42));
	}

	@Test
	void read_ShouldReturnUser() throws Exception {
		UserDtoFull response = new UserDtoFull(1L, "blik", "blik@test.com", 42, LocalDateTime.now());
		when(service.read(1L)).thenReturn(response);
		mockMvc.perform(get("/users/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.name").value("blik"))
				.andExpect(jsonPath("$.email").value("blik@test.com")).andExpect(jsonPath("$.age").value(42));
	}

	@Test
	void update_ShouldReturnUser() throws Exception {
		UserDtoFull request = new UserDtoFull(1L, "blik", "blik@test.com", 42, LocalDateTime.now());
		UserDtoFull response = new UserDtoFull(1L, "blik", "blik@test.com", 42, LocalDateTime.now());

		when(service.update(eq(1L), any(UserDtoFull.class))).thenReturn(response);

		mockMvc.perform(put("/users/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk());
		ArgumentCaptor<UserDtoFull> captor = ArgumentCaptor.forClass(UserDtoFull.class);

		verify(service).update(eq(1L), captor.capture());

		assertEquals("blik", captor.getValue().getName());
		assertEquals(42, captor.getValue().getAge());
	}

	@Test
	void delete_ShouldCallServiceWithId() throws Exception {
		mockMvc.perform(delete("/users/1")).andExpect(status().isOk());
		
		verify(service).delete(1L);
	}
}
