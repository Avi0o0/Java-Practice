package com.akshat.practice.app.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.akshat.practice.app.beans.request.DepartmentRequest;
import com.akshat.practice.app.entity.Department;
import com.akshat.practice.app.exception.ResourceNotFoundException;

@Service
public class DepartmentService {
	
	private Logger logger = LoggerFactory.getLogger(DepartmentService.class);

	private final DatabaseService databaseService;
	private final ModelMapper mapper;
	
	public DepartmentService(ModelMapper mapper, DatabaseService databaseService) {
		this.mapper = mapper;
		this.databaseService = databaseService;
	}

	public List<Department> getAllDepartments() {
		return databaseService.findAllDepartments();
	}

	public Department getDepartmentById(Integer id) {
		logger.info("Fetching dept data for ID {}", id);
		return databaseService.findDeptById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department with ID " + id + " not found"));
	}

	public void addDepartment(DepartmentRequest deptRequest) {
		Department dept = new Department(deptRequest);
		logger.info("New Department Added");
		databaseService.saveDept(dept);
	}

	public void updateDepartment(DepartmentRequest deptRequest, Integer id) {
		Department deptData = databaseService.findDeptById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department With ID: " + id + " not found"));
		
		mapper.map(deptRequest, deptData);

		logger.info("Updating the department details for Id {}", id);
		databaseService.saveDept(deptData);
	}

	public void deleteAllDepartments() {
		logger.info("Deleting all the departments.");
		databaseService.deleteAllDepts();
	}

	public void deleteDepartmentById(Integer id) {
		logger.info("Deleting department details for ID {}", id);
		databaseService.deleteDeptById(id);
	}

	public List<Department> getDepartmentsByEmployee(Integer empId) {
		logger.info("Fetching all department of employee with ID {}", empId);
		List<Department> departments = databaseService.findDepartmentsByEmployeeId(empId);
	    if (departments.isEmpty()) {
	        throw new ResourceNotFoundException("Employee with employee ID " + empId + " not found");
	    }
	    return departments;
	}
}
