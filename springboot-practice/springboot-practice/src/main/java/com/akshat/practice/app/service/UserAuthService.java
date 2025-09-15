package com.akshat.practice.app.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.akshat.practice.app.entity.UserPrincipal;
import com.akshat.practice.app.repository.UserRepository;

@Service
public class UserAuthService implements UserDetailsService {
	
	private UserRepository repository;
	
	public UserAuthService(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	    return repository.findByUsername(username)
	            .map(UserPrincipal::new)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
	}	
}
