package com.practice.app.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserRequest {

	@NotBlank(message = "User Name is required")
	private String username;
	
	@NotBlank(message = "Password is required")
	private String password;
	
	@NotBlank(message = "Role is required: ADMIN or EMPLOYEE")
	@Pattern(regexp = "ADMIN|EMPLOYEE", message = "Type must be ADMIN or EMPLOYEE")
	private String role;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
