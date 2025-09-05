package com.akshat.practice.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.akshat.practice.app.beans.request.DepartmentRequest;
import com.akshat.practice.app.entity.Department;
import com.akshat.practice.app.exception.ResourceNotFoundException;
import com.akshat.practice.app.repository.DepartmentRepository;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        department = new Department();
        department.setDeptId(1);
        department.setDeptName("IT");
        department.setDeptHead("John Doe");
        System.out.println("➡ Running test: " + testInfo.getDisplayName());
    }
    
    @AfterEach
    void afterEach(TestInfo testInfo) throws InterruptedException {
        System.out.println("✅ Completed test: " + testInfo.getDisplayName());
    }

    @Test
   // @DisplayName("Get All Department Test")
    void testGetAllDepartments() {
        when(departmentRepository.findAll()).thenReturn(Arrays.asList(department));

        List<Department> result = departmentService.getAllDepartments();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("IT", result.get(0).getDeptName());

        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void testGetDepartmentById_Found() {
        when(departmentRepository.findById(1)).thenReturn(Optional.of(department));

        Department result = departmentService.getDepartmentById(1);

        assertNotNull(result);
        assertEquals("IT", result.getDeptName());

        verify(departmentRepository, times(1)).findById(1);
    }

    @Test
    void testGetDepartmentById_NotFound() {
        when(departmentRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> departmentService.getDepartmentById(99));

        verify(departmentRepository, times(1)).findById(99);
    }

    @Test
    void testAddDepartment() {
        DepartmentRequest deptRequest = new DepartmentRequest();
        deptRequest.setDeptName("Finance");
        deptRequest.setDeptHead("Alice");

        Department newDept = new Department(deptRequest);

        when(departmentRepository.save(any(Department.class))).thenReturn(newDept);

        departmentService.addDepartment(deptRequest);

        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    void testUpdateDepartment_Found() {
        DepartmentRequest deptRequest = new DepartmentRequest();
        deptRequest.setDeptName("HR");
        deptRequest.setDeptHead("Bob");

        when(departmentRepository.findById(1)).thenReturn(Optional.of(department));

        // Simulate mapper behavior
        doAnswer(invocation -> {
            DepartmentRequest source = invocation.getArgument(0);
            Department target = invocation.getArgument(1);
            target.setDeptName(source.getDeptName());
            target.setDeptHead(source.getDeptHead());
            return null;
        }).when(mapper).map(any(DepartmentRequest.class), any(Department.class));

        departmentService.updateDepartment(deptRequest, 1);

        assertEquals("HR", department.getDeptName());
        assertEquals("Bob", department.getDeptHead());

        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    void testUpdateDepartment_NotFound() {
        DepartmentRequest deptRequest = new DepartmentRequest();
        when(departmentRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> departmentService.updateDepartment(deptRequest, 99));

        verify(departmentRepository, times(1)).findById(99);
    }

    @Test
    void testDeleteAllDepartments() {
        departmentService.deleteAllDepartments();

        verify(departmentRepository, times(1)).deleteAll();
    }

    @Test
    void testDeleteDepartmentById() {
        departmentService.deleteDepartmentById(1);

        verify(departmentRepository, times(1)).deleteById(1);
    }

    @Test
    void testGetDepartmentsByEmployee_Found() {
        when(departmentRepository.findDepartmentsByEmployeeId(1))
                .thenReturn(Arrays.asList(department));

        List<Department> result = departmentService.getDepartmentsByEmployee(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("IT", result.get(0).getDeptName());

        verify(departmentRepository, times(1)).findDepartmentsByEmployeeId(1);
    }

    @Test
    void testGetDepartmentsByEmployee_NotFound() {
        when(departmentRepository.findDepartmentsByEmployeeId(99))
                .thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class,
                () -> departmentService.getDepartmentsByEmployee(99));

        verify(departmentRepository, times(1)).findDepartmentsByEmployeeId(99);
    }
}
