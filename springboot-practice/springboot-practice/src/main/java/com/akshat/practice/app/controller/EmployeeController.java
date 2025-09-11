package com.akshat.practice.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.akshat.practice.app.beans.request.EmployeeRequest;
import com.akshat.practice.app.beans.response.EmployeeResponse;
import com.akshat.practice.app.beans.response.StatusResponse;
import com.akshat.practice.app.constants.AppConstants;
import com.akshat.practice.app.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
public class EmployeeController {

	private final EmployeeService employeeService;
	
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/employees")
	public List<EmployeeResponse> getAllEmployee() {
		return employeeService.getAllEmployees();
	}

	@GetMapping("/employee/{id}")
	public EmployeeResponse getEmployee(@PathVariable Integer id) {
		return employeeService.getEmployee(id);
	}

	@PostMapping("/employee")
	public ResponseEntity<StatusResponse> addEmployees(@Valid @RequestBody EmployeeRequest employee) {
		employeeService.addEmployee(employee);
		return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), AppConstants.SUCCESS));
	}

	@PutMapping("/employee/{id}")
	public ResponseEntity<StatusResponse> updateEmployee(@Valid @RequestBody EmployeeRequest employeeRequest,
			@PathVariable Integer id) {
		StatusResponse response = employeeService.updateEmployee(employeeRequest, id);
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/employee/{id}")
	public ResponseEntity<StatusResponse> patchEmployee(@RequestBody EmployeeRequest employeeRequest,
			@PathVariable Integer id) {
		StatusResponse response = employeeService.patchEmployee(employeeRequest, id);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/employees")
	public ResponseEntity<StatusResponse> deleteAllEmployees() {
		employeeService.deleteAllEmployees();
		return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), AppConstants.SUCCESS));
	}

	@DeleteMapping("employee/{id}")
	public ResponseEntity<StatusResponse> deleteEmployeeByID(@PathVariable Integer id) {
		employeeService.deleteEmployeeByID(id);
		return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), AppConstants.SUCCESS));
	}

	@PutMapping("/employee/{empId}/assign-departments")
	public ResponseEntity<StatusResponse> assignDepartments(@PathVariable Integer empId,
			@RequestBody List<Integer> departmentIds) {

		employeeService.assignDepartments(empId, departmentIds);
		return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), "Departments assigned successfully"));
	}

	@GetMapping("/employees/by-department/{deptId}")
	public List<EmployeeResponse> getEmployeesByDepartment(@PathVariable Integer deptId) {
		return employeeService.getEmployeesByDepartment(deptId);
	}
}
