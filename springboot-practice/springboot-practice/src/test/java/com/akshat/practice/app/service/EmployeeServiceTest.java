package com.akshat.practice.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.akshat.practice.app.repository.DepartmentRepository;
import com.akshat.practice.app.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;
    private ModelMapper mapper;
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employeeRepository = mock(EmployeeRepository.class);
        departmentRepository = mock(DepartmentRepository.class);
        mapper = spy(new ModelMapper()); // spy for real mapping
        employeeService = new EmployeeService(employeeRepository, departmentRepository, mapper);

        employee = new Employee(1, "Alice", "Regular", "IT", "alice@innodeed.com");
    }

    @Test
    void testGetAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));

        List<EmployeeResponse> responses = employeeService.getAllEmployees();

        assertEquals(1, responses.size());
        assertEquals("Alice", responses.get(0).getEmpName());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetEmployee_Found() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        EmployeeResponse response = employeeService.getEmployee(1);

        assertEquals("Alice", response.getEmpName());
        verify(employeeRepository).findById(1);
    }

    @Test
    void testGetEmployee_NotFound() {
        when(employeeRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployee(2));
    }

    @Test
    void testAddEmployee_Success() {
        EmployeeRequest request = new EmployeeRequest(1, "Alice", "Regular", "IT", "alice@innodeed.com");

        when(employeeRepository.existsById(1)).thenReturn(false);
        when(employeeRepository.existsByEmpEmail("alice@innodeed.com")).thenReturn(false);

        employeeService.addEmployee(request);

        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void testAddEmployee_AlreadyExistsById() {
        EmployeeRequest request = new EmployeeRequest(1, "Alice", "Regular", "IT", "alice@innodeed.com");

        when(employeeRepository.existsById(1)).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> employeeService.addEmployee(request));
    }

    @Test
    void testAddEmployee_AlreadyExistsByEmail() {
        EmployeeRequest request = new EmployeeRequest(1, "Alice", "Regular", "IT", "alice@innodeed.com");

        when(employeeRepository.existsById(1)).thenReturn(false);
        when(employeeRepository.existsByEmpEmail("alice@innodeed.com")).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> employeeService.addEmployee(request));
    }

    @Test
    void testUpdateEmployee_Success() {
        EmployeeRequest request = new EmployeeRequest(1, "Bob", "Contractual", "HR", "bob@innodeed.com");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        StatusResponse response = employeeService.updateEmployee(request, 1);

        assertEquals("Bob", employee.getEmpName());
        assertEquals("Contractual", employee.getEmpType());
        assertEquals(200, response.getStatus());
        verify(employeeRepository).save(employee);
    }

    @Test
    void testUpdateEmployee_NotFound() {
        EmployeeRequest request = new EmployeeRequest(1, "Bob", "Contractual", "HR", "bob@innodeed.com");

        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployee(request, 1));
    }

    @Test
    void testPatchEmployee_PartialUpdate() {
        EmployeeRequest request = new EmployeeRequest(null, "Charlie", null, null, null);

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        StatusResponse response = employeeService.patchEmployee(request, 1);

        assertEquals("Charlie", employee.getEmpName()); // only name updated
        assertEquals("Regular", employee.getEmpType()); // unchanged
        assertEquals(200, response.getStatus());
        verify(employeeRepository).save(employee);
    }

    @Test
    void testDeleteAllEmployees() {
        employeeService.deleteAllEmployees();

        verify(employeeRepository).deleteAll();
    }

    @Test
    void testDeleteEmployeeById_Success() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        employeeService.deleteEmployeeByID(1);

        verify(employeeRepository).deleteById(1);
    }

    @Test
    void testDeleteEmployeeById_NotFound() {
        when(employeeRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteEmployeeByID(2));
    }

    @Test
    void testAssignDepartments_Success() {
        Department dept = new Department(10, "HR", "Bob");

        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(departmentRepository.findAllById(Arrays.asList(10))).thenReturn(Collections.singletonList(dept));

        employeeService.assignDepartments(1, Arrays.asList(10));

        assertTrue(employee.getDepartments().contains(dept));
        verify(employeeRepository).save(employee);
    }

    @Test
    void testAssignDepartments_NoDepartmentsFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(departmentRepository.findAllById(Collections.singletonList(10)))
                .thenReturn(Collections.emptyList());

        Runnable action = () -> employeeService.assignDepartments(1, Collections.singletonList(10));

        assertThrows(ResourceNotFoundException.class, action::run);
    }


    @Test
    void testGetEmployeesByDepartment_Success() {
        when(employeeRepository.findEmployeesByDepartmentId(10)).thenReturn(Collections.singletonList(employee));

        List<EmployeeResponse> responses = employeeService.getEmployeesByDepartment(10);

        assertEquals(1, responses.size());
        assertEquals("Alice", responses.get(0).getEmpName());
    }

    @Test
    void testGetEmployeesByDepartment_NotFound() {
        when(employeeRepository.findEmployeesByDepartmentId(10)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeesByDepartment(10));
    }
}
