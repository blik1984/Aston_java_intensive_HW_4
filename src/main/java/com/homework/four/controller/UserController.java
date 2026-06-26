package com.homework.four.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homework.four.dto.UserDtoFull;
import com.homework.four.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor 
@Slf4j

public class UserController {

	private final UserService service;

	@PostMapping
	public UserDtoFull create(@RequestBody UserDtoFull user) {
			return service.create(user);
	}
	
	@GetMapping("/{id}")
	public UserDtoFull get(@PathVariable Long id) {
		return service.read(id);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
	
	@PutMapping("/{id}")
	public UserDtoFull update(@PathVariable Long id, @RequestBody UserDtoFull user) {
		return service.update(id, user);
	}
}
