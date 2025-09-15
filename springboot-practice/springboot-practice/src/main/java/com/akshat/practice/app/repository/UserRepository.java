package com.akshat.practice.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akshat.practice.app.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

	@Query(value = "SELECT * FROM users u WHERE LOWER(u.username) = LOWER(:username)", nativeQuery = true)
	Optional<Users> findByUsername(@Param("username") String username);
}
