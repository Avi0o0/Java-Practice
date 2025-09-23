package com.akshat.practice.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.akshat.practice.app.entity.Department;
import com.akshat.practice.app.entity.Employee;
import com.akshat.practice.app.repository.DepartmentRepository;
import com.akshat.practice.app.repository.EmployeeRepository;

@Component
public class DatabaseService {

	private final DepartmentRepository departmentRepository;
	private final EmployeeRepository employeeRepository;

	public DatabaseService(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }
	
	//Employee Repository
	public List<Employee> findEmployeesByDepartmentId(Integer deptId){
		return employeeRepository.findEmployeesByDepartmentId(deptId);
	}
	
	public boolean existsByEmpEmail(String empEmail) {
		return employeeRepository.existsByEmpEmail(empEmail);	
	}
	
	public List<Employee> findAllEmployees() {
		return employeeRepository.findAll();
	}

	//Department Repository
	public List<Department> findDepartmentsByEmployeeId(Integer empId){
		return departmentRepository.findDepartmentsByEmployeeId(empId);
	}

	public Optional<Department> findDeptById(Integer id) {
		return departmentRepository.findById(id);
	}

	public List<Department> findAllDepartments() {
		return departmentRepository.findAll();
	}

	public void saveDept(Department dept) {
		departmentRepository.save(dept);
	}

	public void deleteAllDepts() {
		departmentRepository.deleteAll();
	}

	public void deleteDeptById(Integer id) {
		departmentRepository.deleteById(id);
	}

	public List<Department> findAllDeptsById(List<Integer> departmentIds) {
		return departmentRepository.findAllById(departmentIds);
	}
//------------------
	public Optional<Employee> findEmpById(Integer id) {
		return employeeRepository.findById(id);
	}

	public void saveEmp(Employee emp) {
		employeeRepository.save(emp);
	}

	public void deleteAllEmps() {
		employeeRepository.deleteAll();
	}

	public void deleteEmpById(Integer id) {
		employeeRepository.deleteById(id);
	}
	
	
}
