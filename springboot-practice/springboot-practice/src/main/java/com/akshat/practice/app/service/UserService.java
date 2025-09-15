package com.akshat.practice.app.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.akshat.practice.app.beans.request.UserRequest;
import com.akshat.practice.app.beans.response.UserResponse;
import com.akshat.practice.app.entity.Users;
import com.akshat.practice.app.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class UserService {
	
	private UserRepository repository;
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
	private AuthenticationManager authenticationManager;
	private JWTService jwtService;
	
	public UserService(UserRepository repository, AuthenticationManager authenticationManager, JWTService jwtService) {
		this.repository = repository;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	public void addUser(UserRequest request) {
		Users user = new Users(request);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		repository.save(user);
	}
	
	public void deleteUser(Integer id) {
		repository.deleteById(id);
	}
	
	public List<UserResponse> getAllUsers() {
		List<Users> usersList = repository.findAll();
		
		return usersList.stream().map(userList -> new UserResponse(userList.getId(),
				userList.getUsername(), userList.getPassword(), userList.getRole())).toList();
	}

	public String verify(@Valid UserRequest user) {
		Authentication authentication = authenticationManager.authenticate
				(new  UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		
		if (authentication.isAuthenticated()) {
			return jwtService.generateToken(user.getUsername());
		}
		
		return null;
	}
}
