package com.akshat.practice.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.akshat.practice.app.beans.request.DepartmentRequest;
import com.akshat.practice.app.entity.Department;
import com.akshat.practice.app.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class DepartmentServiceTest {

    @Mock
    private DatabaseService databaseService;

    @Spy
    private final ModelMapper mapper = new ModelMapper();

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;

    @BeforeAll
    static void beforeAllSetup() {
        System.out.println("Starting Unit test for DepartmentService");
    }

    @AfterAll
    static void afterAllSetup() {
        System.out.println("Unit test completed for DepartmentService");
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        department = new Department();
        department.setDeptId(1);
        department.setDeptName("IT");
        department.setDeptHead("John Doe");
        System.out.println("➡ Running test: " + testInfo.getDisplayName());
    }

    @AfterEach
    void afterEach(TestInfo testInfo) {
        System.out.println("✅ Completed test: " + testInfo.getDisplayName());
    }

    @Test
    void testGetAllDepartments() {
        when(databaseService.findAllDepartments()).thenReturn(Arrays.asList(department));

        List<Department> result = departmentService.getAllDepartments();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("IT", result.get(0).getDeptName());

        verify(databaseService, times(1)).findAllDepartments();
    }

    @Test
    void testGetDepartmentById_Found() {
        when(databaseService.findDeptById(1)).thenReturn(Optional.of(department));

        Department result = departmentService.getDepartmentById(1);

        assertNotNull(result);
        assertEquals("IT", result.getDeptName());
        verify(databaseService, times(1)).findDeptById(1);
    }

    @Test
    void testGetDepartmentById_NotFound() {
        when(databaseService.findDeptById(99)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> departmentService.getDepartmentById(99));
        verify(databaseService, times(1)).findDeptById(99);
        assertEquals("Department with ID 99 not found", exception.getMessage());
    }

    @Test
    void testAddDepartment() {
        DepartmentRequest deptRequest = new DepartmentRequest();
        deptRequest.setDeptName("Finance");
        deptRequest.setDeptHead("Alice");
        doNothing().when(databaseService).saveDept(any(Department.class));

        departmentService.addDepartment(deptRequest);

        verify(databaseService, times(1)).saveDept(any(Department.class));
    }

    @Test
    void testUpdateDepartment_Found() {
        DepartmentRequest deptRequest = new DepartmentRequest();
        deptRequest.setDeptName("HR");
        deptRequest.setDeptHead("Bob");

        when(databaseService.findDeptById(1)).thenReturn(Optional.of(department));

        departmentService.updateDepartment(deptRequest, 1);

        assertEquals("HR", department.getDeptName());
        assertEquals("Bob", department.getDeptHead());
        verify(databaseService, times(1)).saveDept(department);
    }

    @Test
    void testUpdateDepartment_NotFound() {
        DepartmentRequest deptRequest = new DepartmentRequest();
        when(databaseService.findDeptById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> departmentService.updateDepartment(deptRequest, 99));
        verify(databaseService, times(1)).findDeptById(99);
    }

    @Test
    void testDeleteAllDepartments() {
        departmentService.deleteAllDepartments();
        verify(databaseService, times(1)).deleteAllDepts();
    }

    @Test
    void testDeleteDepartmentById() {
        departmentService.deleteDepartmentById(1);
        verify(databaseService, times(1)).deleteDeptById(1);
    }

    @Test
    void testGetDepartmentsByEmployee_Found() {
        when(databaseService.findDepartmentsByEmployeeId(1))
                .thenReturn(Arrays.asList(department));

        List<Department> result = departmentService.getDepartmentsByEmployee(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("IT", result.get(0).getDeptName());
        verify(databaseService, times(1)).findDepartmentsByEmployeeId(1);
    }

    @Test
    void testGetDepartmentsByEmployee_NotFound() {
        when(databaseService.findDepartmentsByEmployeeId(99))
                .thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class,
                () -> departmentService.getDepartmentsByEmployee(99));
        verify(databaseService, times(1)).findDepartmentsByEmployeeId(99);
    }

    @Test
    @EnabledOnOs(value = OS.WINDOWS, disabledReason = "Enabled only on Windows")
    void testDeleteAllDepartmentsOnlyOnWindows() {
        departmentService.deleteAllDepartments();
        verify(databaseService, times(1)).deleteAllDepts();
    }
}
