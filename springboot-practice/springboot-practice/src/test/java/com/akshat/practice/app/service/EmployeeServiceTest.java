package com.akshat.practice.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.akshat.practice.app.beans.request.EmployeeRequest;
import com.akshat.practice.app.beans.response.EmployeeResponse;
import com.akshat.practice.app.entity.Employee;
import com.akshat.practice.app.exception.ResourceNotFoundException;
import com.akshat.practice.app.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private ModelMapper mapper;

	@InjectMocks
	private EmployeeService employeeService;

	private Employee employee;

	@BeforeAll
	void beforeAllSetup() {
		System.out.println("Starting Unit test for class: " + EmployeeServiceTest.class.toString());
	}

	@AfterAll
	void afterAllSetup() {
		System.out.println("Unit test completed for class: " + EmployeeServiceTest.class.toString());
	}

	@BeforeEach
	void setUp(TestInfo testInfo) {
		employee = new Employee();
		employee.setEmpId(1);
		employee.setEmpField("IT");
		employee.setEmpName("John Doe");
		employee.setEmpType("Regular");
		System.out.println("→ Mock Employee info setup done for test: " + testInfo.getDisplayName());
		System.out.println("➡ Running test: " + testInfo.getDisplayName());
	}

	@AfterEach
	void afterEach(TestInfo testInfo) {
		System.out.println("✅ Completed test: " + testInfo.getDisplayName());
	}

	@Test
	void testGetAllEmployees() {
		when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));

		List<EmployeeResponse> employees = employeeService.getAllEmployees();
		assertNotNull(employees);
		assertEquals(1, employees.size());
		assertEquals(1, employees.get(0).getEmpid());
	}

	@Test
	void testGetEmployeeByID() {
		when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

		EmployeeResponse employeeResponse = employeeService.getEmployee(1);
		assertNotNull(employeeResponse);
		assertEquals(1, employeeResponse.getEmpid());
		assertEquals("John Doe", employeeResponse.getEmpName());
	}

	@Test
	void testGetDepartmentById_NotFound() {
		when(employeeRepository.findById(99)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployee(99));
		verify(employeeRepository, times(1)).findById(99);
	}

	@Test
	void testAddEmployee() {
		EmployeeRequest employeeRequest = new EmployeeRequest(1, "Akshat", "Regular", "IT", "akshat.gupta@innodeed.com");
		
		Employee employeeEntity = new Employee();
		employeeEntity.setEmpField(employeeRequest.getEmpField());
		employeeEntity.setEmpId(employeeRequest.getEmpid());
		employeeEntity.setEmpName(employeeRequest.getEmpName());
		employeeEntity.setEmpType(employeeRequest.getEmpType());
		employeeEntity.setEmpEmail(employeeRequest.getEmpEmail());
		
		when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
		employeeService.addEmployee(employeeRequest);
		verify(employeeRepository, times(1)).save(any(Employee.class));
	}
}
