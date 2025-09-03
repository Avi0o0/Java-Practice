package com.akshat.practice.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.akshat.practice.app.beans.Department;
import com.akshat.practice.app.beans.request.DepartmentRequest;
import com.akshat.practice.app.beans.response.StatusResponse;
import com.akshat.practice.app.service.DepartmentService;

@RestController
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@GetMapping("/departments")
	public List<Department> getAllDepartments() {
		return departmentService.getAllDepartments();
	}

	@GetMapping("/department/{id}")
	public Department getDepartment(@PathVariable Integer id) {
		return departmentService.getDepartmentById(id);
	}

	@PostMapping("/department")
	public ResponseEntity<StatusResponse> addDepartment(@RequestBody DepartmentRequest deptRequest) {
		departmentService.addDepartment(deptRequest);
		return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), "Success"));
	}

	@PutMapping("/department/{id}")
	public ResponseEntity<StatusResponse> updateDepartment(@RequestBody DepartmentRequest deptRequest, @PathVariable Integer id) {
		departmentService.updateDepartment(deptRequest, id);
		return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), "Success"));
	}

	@DeleteMapping("/departments")
	public ResponseEntity<StatusResponse> deleteAllDepartments() {
		departmentService.deleteAllDepartments();
		return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), "Success"));
	}

	@DeleteMapping("department/{id}")
	public ResponseEntity<StatusResponse> deleteDepartmentById(@PathVariable Integer id) {
		departmentService.deleteDepartmentById(id);
		return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), "Success"));
	}

	@GetMapping("/departments/by-employee/{empId}")
	public List<Department> getDepartmentsByEmployee(@PathVariable Integer empId) {
		return departmentService.getDepartmentsByEmployee(empId);
	}
}
