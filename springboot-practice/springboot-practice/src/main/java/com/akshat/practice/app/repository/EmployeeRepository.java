package com.akshat.practice.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akshat.practice.app.beans.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	
}