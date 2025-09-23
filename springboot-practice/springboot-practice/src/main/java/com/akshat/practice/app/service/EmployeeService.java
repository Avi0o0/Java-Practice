package com.akshat.practice.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.akshat.practice.app.beans.request.EmployeeRequest;
import com.akshat.practice.app.beans.response.EmployeeResponse;
import com.akshat.practice.app.beans.response.StatusResponse;
import com.akshat.practice.app.entity.Department;
import com.akshat.practice.app.entity.Employee;
import com.akshat.practice.app.exception.AlreadyExistException;
import com.akshat.practice.app.exception.ResourceNotFoundException;

@Service
public class EmployeeService {
	
	private Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	private final DatabaseService databaseService;
    private final ModelMapper mapper;

    // Spring automatically injects beans via this constructor
    public EmployeeService(DatabaseService databaseService, ModelMapper mapper) {
        this.databaseService = databaseService;
        this.mapper = mapper;
    }

	public List<EmployeeResponse> getAllEmployees() {
		logger.info("Fetch Employee List");
		List<Employee> employeeLists = databaseService.findAllEmployees();
		return employeeLists.stream().map(empList -> new EmployeeResponse(empList.getEmpId(),
				empList.getEmpName(), empList.getEmpType(), empList.getEmpField(), empList.getEmpEmail())).toList();
	}

	public EmployeeResponse getEmployee(Integer id) {
		logger.info("Get Employee By ID {}", id);
		Optional<Employee> employee = databaseService.findEmpById(id);
		return employee
				.map(emp -> new EmployeeResponse(emp.getEmpId(), emp.getEmpName(), emp.getEmpType(), emp.getEmpField(), emp.getEmpEmail()))
				.orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + id + " not found"));
	}

	public void addEmployee(EmployeeRequest employee) {
		logger.info("Add new employee");
        if (databaseService.existsByEmpEmail(employee.getEmpEmail())) {
            throw new AlreadyExistException("Employee with email " + employee.getEmpEmail() + " already exists");
        }
        
		Employee emp = new Employee(employee.getEmpid(), employee.getEmpName(), employee.getEmpType(),
				employee.getEmpField(), employee.getEmpEmail());
		databaseService.saveEmp(emp);
	}

	public StatusResponse updateEmployee(EmployeeRequest employee, Integer id) {
		logger.info("Updating employee details with ID {}", id);
		Employee employeeData = databaseService.findEmpById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Employee ID: " + id));
		
		mapper.map(employee, employeeData);
		databaseService.saveEmp(employeeData);
		return new StatusResponse(HttpStatus.OK.value(), "Employee Data Updated Successfully!");
	}
	
	public StatusResponse patchEmployee(EmployeeRequest employee, Integer id) {
		Employee employeeData = databaseService.findEmpById(id)
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
	    
	    databaseService.saveEmp(employeeData);
	    
		return new StatusResponse(HttpStatus.OK.value(), "Employee Data Updated Successfully!");
	}

	public void deleteAllEmployees() {
		logger.info("Deleting all employees");
		databaseService.deleteAllEmps();
	}

	public void deleteEmployeeByID(Integer id) {
		logger.info("Deleting emp with ID {}", id);
		databaseService.findEmpById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
		
		databaseService.deleteEmpById(id);
	}

	public void assignDepartments(Integer empId, List<Integer> departmentIds) {
		Employee employee = databaseService.findEmpById(empId)
				.orElseThrow(() -> new RuntimeException("Employee not found with id " + empId));

		List<Department> departments = databaseService.findAllDeptsById(departmentIds);

		if (departments.isEmpty()) {
			throw new ResourceNotFoundException("No departments found for ids " + departmentIds);
		}

		employee.setDepartments(new HashSet<>(departments));
		databaseService.saveEmp(employee);
	}

	public List<EmployeeResponse> getEmployeesByDepartment(Integer deptId) {
		List<Employee> employeeLists = databaseService.findEmployeesByDepartmentId(deptId);

		if (employeeLists.isEmpty()) {
			throw new ResourceNotFoundException("Department with department ID " + deptId + " not found");
		}

		return employeeLists.stream()
				.map(emp -> new EmployeeResponse(emp.getEmpId(), emp.getEmpName(), emp.getEmpType(), emp.getEmpField(), emp.getEmpEmail()))
				.toList();
	}
}
