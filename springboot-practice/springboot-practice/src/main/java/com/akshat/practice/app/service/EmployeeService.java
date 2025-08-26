package com.akshat.practice.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akshat.practice.app.beans.Employee;
import com.akshat.practice.app.beans.request.EmployeeRequest;
import com.akshat.practice.app.beans.response.EmployeeResponse;
import com.akshat.practice.app.exception.ResourceNotFoundException;
import com.akshat.practice.app.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	public List<EmployeeResponse> getAllEmployees() {
		List<Employee> lists = employeeRepository.findAll();
		List<EmployeeResponse> respList = lists.stream()
				.map(list -> new EmployeeResponse(
						list.getEmpId(), list.getEmpName(), list.getEmpType(), list.getEmpField()))
				.toList();
		return respList;
	}

	public EmployeeResponse getEmployee(int id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		EmployeeResponse response = employee
		        .map(emp -> new EmployeeResponse(
		                emp.getEmpId(),
		                emp.getEmpName(),
		                emp.getEmpType(),
		                emp.getEmpField()
		        ))
		        .orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + id + " not found"));
		return response;
	}

	public void addEmployee(EmployeeRequest employee) {
		Employee emp = new Employee(employee.getEmpid(), employee.getEmpName(), employee.getEmpType(), employee.getEmpField());
		employeeRepository.save(emp);
	}

	public void updateEmployee(EmployeeRequest employee, int id) {
		if(employee.getEmpid() == id) {
			Employee emp = new Employee(employee.getEmpid(), employee.getEmpName(), employee.getEmpType(), employee.getEmpField());
			employeeRepository.save(emp);
		}
	}

	public void deleteAllEmployees() {
		employeeRepository.deleteAll();
	}

	public void deleteEmployeeByID(int id) {
		employeeRepository.deleteById(id);
	}

}
