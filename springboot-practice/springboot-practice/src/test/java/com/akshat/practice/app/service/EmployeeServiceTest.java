package com.akshat.practice.app.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.akshat.practice.app.beans.request.EmployeeRequest;
import com.akshat.practice.app.beans.response.EmployeeResponse;
import com.akshat.practice.app.beans.response.StatusResponse;
import com.akshat.practice.app.entity.Department;
import com.akshat.practice.app.entity.Employee;
import com.akshat.practice.app.exception.AlreadyExistException;
import com.akshat.practice.app.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    private DatabaseService databaseService;
    private ModelMapper mapper;
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        databaseService = mock(DatabaseService.class);
        mapper = spy(new ModelMapper());
        employeeService = new EmployeeService(databaseService, mapper);

        employee = new Employee(1, "Alice", "Regular", "IT", "alice@innodeed.com");
    }

    @Test
    void testGetAllEmployees() {
        when(databaseService.findAllEmployees()).thenReturn(Collections.singletonList(employee));

        List<EmployeeResponse> responses = employeeService.getAllEmployees();

        assertEquals(1, responses.size());
        assertEquals("Alice", responses.get(0).getEmpName());
        verify(databaseService).findAllEmployees();
    }

    @Test
    void testGetEmployee_Found() {
        when(databaseService.findEmpById(1)).thenReturn(Optional.of(employee));

        EmployeeResponse response = employeeService.getEmployee(1);

        assertEquals("Alice", response.getEmpName());
        verify(databaseService).findEmpById(1);
    }

    @Test
    void testGetEmployee_NotFound() {
        when(databaseService.findEmpById(2)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployee(2));
    }

    @Test
    void testAddEmployee_Success() {
        EmployeeRequest request = new EmployeeRequest(1, "Alice", "Regular", "IT", "alice@innodeed.com");

        when(databaseService.existsByEmpEmail("alice@innodeed.com")).thenReturn(false);

        employeeService.addEmployee(request);

        verify(databaseService).saveEmp(any(Employee.class));
    }

    @Test
    void testAddEmployee_AlreadyExistsByEmail() {
        EmployeeRequest request = new EmployeeRequest(1, "Alice", "Regular", "IT", "alice@innodeed.com");

        when(databaseService.existsByEmpEmail("alice@innodeed.com")).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> employeeService.addEmployee(request));
    }

    @Test
    void testUpdateEmployee_Success() {
        EmployeeRequest request = new EmployeeRequest(1, "Bob", "Contractual", "HR", "bob@innodeed.com");

        when(databaseService.findEmpById(1)).thenReturn(Optional.of(employee));

        StatusResponse response = employeeService.updateEmployee(request, 1);

        assertEquals("Bob", employee.getEmpName());
        assertEquals("Contractual", employee.getEmpType());
        assertEquals(200, response.getStatus());
        verify(databaseService).saveEmp(employee);
    }

    @Test
    void testUpdateEmployee_NotFound() {
        EmployeeRequest request = new EmployeeRequest(1, "Bob", "Contractual", "HR", "bob@innodeed.com");

        when(databaseService.findEmpById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployee(request, 1));
    }

    @Test
    void testPatchEmployee_PartialUpdate() {
        EmployeeRequest request = new EmployeeRequest(null, "Charlie", null, null, null);

        when(databaseService.findEmpById(1)).thenReturn(Optional.of(employee));

        StatusResponse response = employeeService.patchEmployee(request, 1);

        assertEquals("Charlie", employee.getEmpName()); // only name updated
        assertEquals("Regular", employee.getEmpType()); // unchanged
        assertEquals(200, response.getStatus());
        verify(databaseService).saveEmp(employee);
    }

    @Test
    void testDeleteAllEmployees() {
        employeeService.deleteAllEmployees();

        verify(databaseService).deleteAllEmps();
    }

    @Test
    void testDeleteEmployeeById_Success() {
        when(databaseService.findEmpById(1)).thenReturn(Optional.of(employee));

        employeeService.deleteEmployeeByID(1);

        verify(databaseService).deleteEmpById(1);
    }

    @Test
    void testDeleteEmployeeById_NotFound() {
        when(databaseService.findEmpById(2)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteEmployeeByID(2));
    }

    @Test
    void testAssignDepartments_Success() {
        Department dept = new Department(10, "HR", "Bob");

        when(databaseService.findEmpById(1)).thenReturn(Optional.of(employee));
        when(databaseService.findAllDeptsById(Arrays.asList(10))).thenReturn(Collections.singletonList(dept));

        employeeService.assignDepartments(1, Arrays.asList(10));

        assertTrue(employee.getDepartments().contains(dept));
        verify(databaseService).saveEmp(employee);
    }

    @Test
    void testAssignDepartments_NoDepartmentsFound() {
        when(databaseService.findEmpById(1)).thenReturn(Optional.of(employee));
        when(databaseService.findAllDeptsById(Collections.singletonList(10)))
                .thenReturn(Collections.emptyList());

        List<Integer> deptIds = Collections.singletonList(10);
        assertThrows(ResourceNotFoundException.class, () -> employeeService.assignDepartments(1, deptIds));
    }

    @Test
    void testGetEmployeesByDepartment_Success() {
        when(databaseService.findEmployeesByDepartmentId(10)).thenReturn(Collections.singletonList(employee));

        List<EmployeeResponse> responses = employeeService.getEmployeesByDepartment(10);

        assertEquals(1, responses.size());
        assertEquals("Alice", responses.get(0).getEmpName());
    }

    @Test
    void testGetEmployeesByDepartment_NotFound() {
        when(databaseService.findEmployeesByDepartmentId(10)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeesByDepartment(10));
    }
}
