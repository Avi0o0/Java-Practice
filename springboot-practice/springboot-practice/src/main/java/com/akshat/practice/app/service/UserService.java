package com.akshat.practice.app.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.akshat.practice.app.entity.UserPrincipal;
import com.akshat.practice.app.entity.Users;
import com.akshat.practice.app.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("User Name + " + username);
		Users user = userRepository.findByUsername(username);
		if(user == null) {
			System.out.println("False");
			System.out.println("User not found + " + username);
			throw new UsernameNotFoundException("User not found");
		}
		System.out.println("True");
		System.out.println("returning eith user: " + user.getUserName());
		return new UserPrincipal(user);
	}
	
//	public void addUser(UserRequest request) {
//		Users user = new Users(request);
//		userRepository.save(user);
//	}
//	
//	public void deleteUser(Integer id) {
//		userRepository.deleteById(id);
//	}
//	
//	public List<UserResponse> getAllUsers() {
//		List<Users> usersList = userRepository.findAll();
//		
//		return usersList.stream().map(userList -> new UserResponse(userList.getId(),
//				userList.getUserName(), userList.getPassword(), userList.getRole())).toList();
//	}

}
