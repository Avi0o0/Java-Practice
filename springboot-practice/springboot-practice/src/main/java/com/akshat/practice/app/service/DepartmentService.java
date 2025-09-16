package com.akshat.practice.app.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.akshat.practice.app.beans.request.DepartmentRequest;
import com.akshat.practice.app.entity.Department;
import com.akshat.practice.app.exception.ResourceNotFoundException;
import com.akshat.practice.app.repository.DepartmentRepository;

@Service
public class DepartmentService {

	private final DepartmentRepository departmentRepository;
	private final ModelMapper mapper;
	
	public DepartmentService(DepartmentRepository departmentRepository, ModelMapper mapper) {
		this.departmentRepository = departmentRepository;
		this.mapper = mapper;
	}

	public List<Department> getAllDepartments() {
		return departmentRepository.findAll();
	}

	public Department getDepartmentById(Integer id) {
		return departmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department with ID " + id + " not found"));
	}

	public void addDepartment(DepartmentRequest deptRequest) {
		Department dept = new Department(deptRequest);
		departmentRepository.save(dept);
	}

	public void updateDepartment(DepartmentRequest deptRequest, Integer id) {
		Department deptData = departmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department With ID: " + id + " not found"));
		
		mapper.map(deptRequest, deptData);

	    departmentRepository.save(deptData);
	}

	public void deleteAllDepartments() {
		departmentRepository.deleteAll();
	}

	public void deleteDepartmentById(Integer id) {
		departmentRepository.deleteById(id);
	}

	public List<Department> getDepartmentsByEmployee(Integer empId) {
		List<Department> departments = departmentRepository.findDepartmentsByEmployeeId(empId);
	    if (departments.isEmpty()) {
	        throw new ResourceNotFoundException("Employee with employee ID " + empId + " not found");
	    }
	    return departments;
	}
}
