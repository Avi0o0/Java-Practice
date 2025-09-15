package com.akshat.practice.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.akshat.practice.app.beans.request.UserRequest;
import com.akshat.practice.app.beans.response.StatusResponse;
import com.akshat.practice.app.beans.response.UserResponse;
import com.akshat.practice.app.constants.AppConstants;
import com.akshat.practice.app.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/user")
	public ResponseEntity<StatusResponse> addUser(@Valid @RequestBody UserRequest request) {
		userService.addUser(request);
		return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), AppConstants.SUCCESS));
	}
	
	@GetMapping("/users")
	public List<UserResponse> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@DeleteMapping("/user/{id}")
	public StatusResponse deleteUserByID(@PathVariable Integer id) {
		userService.deleteUser(id);
		return new StatusResponse(HttpStatus.OK.value(), AppConstants.SUCCESS);
	}
	
	@PostMapping("/login")
	public String login(@Valid @RequestBody UserRequest request) {
		return userService.verify(request);
	}
}
