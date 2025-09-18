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
import com.akshat.practice.app.repository.DepartmentRepository;
import com.akshat.practice.app.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	private Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ModelMapper mapper;

    // Spring automatically injects beans via this constructor
    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.mapper = mapper;
    }

	public List<EmployeeResponse> getAllEmployees() {
		logger.info("Fetch Employee List");
		List<Employee> employeeLists = employeeRepository.findAll();
		return employeeLists.stream().map(empList -> new EmployeeResponse(empList.getEmpId(),
				empList.getEmpName(), empList.getEmpType(), empList.getEmpField(), empList.getEmpEmail())).toList();
	}

	public EmployeeResponse getEmployee(Integer id) {
		logger.info("Get Employee By ID {}", id);
		Optional<Employee> employee = employeeRepository.findById(id);
		return employee
				.map(emp -> new EmployeeResponse(emp.getEmpId(), emp.getEmpName(), emp.getEmpType(), emp.getEmpField(), emp.getEmpEmail()))
				.orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + id + " not found"));
	}

	public void addEmployee(EmployeeRequest employee) {
		logger.info("Add new employee");
        if (employeeRepository.existsByEmpEmail(employee.getEmpEmail())) {
            throw new AlreadyExistException("Employee with email " + employee.getEmpEmail() + " already exists");
        }
        
		Employee emp = new Employee(employee.getEmpid(), employee.getEmpName(), employee.getEmpType(),
				employee.getEmpField(), employee.getEmpEmail());
		employeeRepository.save(emp);
	}

	public StatusResponse updateEmployee(EmployeeRequest employee, Integer id) {
		logger.info("Updating employee details with ID {}", id);
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
		logger.info("Deleting all employees");
		employeeRepository.deleteAll();
	}

	public void deleteEmployeeByID(Integer id) {
		logger.info("Deleting emp with ID {}", id);
		employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
		
		employeeRepository.deleteById(id);
	}

	public void assignDepartments(Integer empId, List<Integer> departmentIds) {
		Employee employee = employeeRepository.findById(empId)
				.orElseThrow(() -> new RuntimeException("Employee not found with id " + empId));

		List<Department> departments = departmentRepository.findAllById(departmentIds);

		if (departments.isEmpty()) {
			throw new ResourceNotFoundException("No departments found for ids " + departmentIds);
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
				.map(emp -> new EmployeeResponse(emp.getEmpId(), emp.getEmpName(), emp.getEmpType(), emp.getEmpField(), emp.getEmpEmail()))
				.toList();
	}
}
