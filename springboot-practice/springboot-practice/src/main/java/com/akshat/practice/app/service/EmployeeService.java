package com.akshat.practice.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.akshat.practice.app.beans.request.EmployeeRequest;
import com.akshat.practice.app.beans.response.EmployeeResponse;
import com.akshat.practice.app.beans.response.StatusResponse;
import com.akshat.practice.app.entity.Department;
import com.akshat.practice.app.entity.Employee;
import com.akshat.practice.app.exception.ResourceNotFoundException;
import com.akshat.practice.app.repository.DepartmentRepository;
import com.akshat.practice.app.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private ModelMapper mapper;

	public List<EmployeeResponse> getAllEmployees() {
		List<Employee> employeeLists = employeeRepository.findAll();
		List<EmployeeResponse> respList = employeeLists.stream().map(empList -> new EmployeeResponse(empList.getEmpId(),
				empList.getEmpName(), empList.getEmpType(), empList.getEmpField())).toList();
		return respList;
	}

	public EmployeeResponse getEmployee(Integer id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		EmployeeResponse response = employee
				.map(emp -> new EmployeeResponse(emp.getEmpId(), emp.getEmpName(), emp.getEmpType(), emp.getEmpField()))
				.orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + id + " not found"));
		return response;
	}

	public void addEmployee(EmployeeRequest employee) {
		Employee emp = new Employee(employee.getEmpid(), employee.getEmpName(), employee.getEmpType(),
				employee.getEmpField());
		employeeRepository.save(emp);
	}

	public StatusResponse updateEmployee(EmployeeRequest employee, Integer id) {
		Employee employeeData = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Employee ID: " + id));
		mapper.map(employee, employeeData);
		employeeRepository.save(employeeData);
		return new StatusResponse(HttpStatus.OK.value(), "Employee Data Updated Successfully!");
	}
	
	public StatusResponse patchEmployee(EmployeeRequest employee, Integer id) {
		Employee employeeData = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Employee ID: " + id));
		
		if (employee.getEmpName() != null && !employee.getEmpName().trim().isEmpty()) {
	        employeeData.setEmpName(employee.getEmpName());
	    }
	    if (employee.getEmpType() != null && !employee.getEmpType().trim().isEmpty()) {
	        employeeData.setEmpType(employee.getEmpType());
	    }
	    if (employee.getEmpField() != null && !employee.getEmpField().trim().isEmpty()) {
	        employeeData.setEmpField(employee.getEmpField());
	    }
	    
	    employeeRepository.save(employeeData);
	    
		return new StatusResponse(HttpStatus.OK.value(), "Employee Data Updated Successfully!");
	}

	public void deleteAllEmployees() {
		employeeRepository.deleteAll();
	}

	public void deleteEmployeeByID(int id) {
		employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
		
		employeeRepository.deleteById(id);
	}

	public void assignDepartments(Integer empId, List<Integer> departmentIds) {
		Employee employee = employeeRepository.findById(empId)
				.orElseThrow(() -> new RuntimeException("Employee not found with id " + empId));

		List<Department> departments = departmentRepository.findAllById(departmentIds);

		if (departments.isEmpty()) {
			throw new RuntimeException("No departments found for ids " + departmentIds);
		}

		employee.setDepartments(new HashSet<>(departments));
		employeeRepository.save(employee);
	}

	public List<EmployeeResponse> getEmployeesByDepartment(Integer deptId) {
		List<Employee> employeeLists = employeeRepository.findEmployeesByDepartmentId(deptId);

		if (employeeLists.isEmpty()) {
			throw new ResourceNotFoundException("Department with department ID " + deptId + " not found");
		}

		return employeeLists.stream()
				.map(emp -> new EmployeeResponse(emp.getEmpId(), emp.getEmpName(), emp.getEmpType(), emp.getEmpField()))
				.toList();
	}
}
