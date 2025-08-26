package com.akshat.practice.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.akshat.practice.app.beans.request.EmployeeRequest;
import com.akshat.practice.app.beans.response.EmployeeResponse;
import com.akshat.practice.app.beans.response.StatusResponse;
import com.akshat.practice.app.service.EmployeeService;

@RestController
public class EmployeeController {
		
		static final Logger logger  = LoggerFactory.getLogger(EmployeeController.class);

		@Autowired
		private EmployeeService employeeService;
		
		@GetMapping("/employees")
		public List<EmployeeResponse> getAllEmployee(){
			return employeeService.getAllEmployees();
		}

		@GetMapping("/employee/{id}")
		public EmployeeResponse getEmployee(@PathVariable int id){
			return employeeService.getEmployee(id);
		}
		
		@PostMapping("/employee/add")
		public ResponseEntity<StatusResponse> addEmployees(@RequestBody EmployeeRequest employee){
			employeeService.addEmployee(employee);
			return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), "Success"));
		}

		@PutMapping("/employee/{id}")
		public ResponseEntity<StatusResponse> updateEmployee(@RequestBody EmployeeRequest e, @PathVariable int id){
			employeeService.updateEmployee(e, id);
			return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), "Success"));
		}
		
		@DeleteMapping("/employees")
		public ResponseEntity<StatusResponse> deleteAllEmployees(){
			employeeService.deleteAllEmployees();
			return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), "Success"));
		}

		@DeleteMapping("employee/{id}")
		public ResponseEntity<StatusResponse> deleteEmployeeByID(@RequestBody EmployeeRequest e, @PathVariable int id){
			employeeService.deleteEmployeeByID(id);
			return ResponseEntity.ok(new StatusResponse(HttpStatus.OK.value(), "Success"));
		}
}
