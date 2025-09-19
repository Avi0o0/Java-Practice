package com.akshat.practice.app.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.akshat.practice.app.beans.request.EmployeeRequest;
import com.akshat.practice.app.beans.response.EmployeeResponse;
import com.akshat.practice.app.beans.response.StatusResponse;
import com.akshat.practice.app.constants.AppConstants;
import com.akshat.practice.app.service.EmployeeService;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void testGetAllEmployees() throws Exception {
        EmployeeResponse response = new EmployeeResponse(1, "John", "Regular", "IT", "john@innodeed.com");

        when(employeeService.getAllEmployees()).thenReturn(Collections.singletonList(response));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].empid").value(1))
                .andExpect(jsonPath("$[0].empName").value("John"))
                .andExpect(jsonPath("$[0].empType").value("Regular"))
                .andExpect(jsonPath("$[0].empField").value("IT"))
                .andExpect(jsonPath("$[0].empEmail").value("john@innodeed.com"));

        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void testGetEmployeeById() throws Exception {
        EmployeeResponse response = new EmployeeResponse(2, "Alice", "Contractual", "HR", "alice@innodeed.com");

        when(employeeService.getEmployee(2)).thenReturn(response);

        mockMvc.perform(get("/employee/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.empid").value(2))
                .andExpect(jsonPath("$.empName").value("Alice"))
                .andExpect(jsonPath("$.empType").value("Contractual"))
                .andExpect(jsonPath("$.empField").value("HR"))
                .andExpect(jsonPath("$.empEmail").value("alice@innodeed.com"));

        verify(employeeService, times(1)).getEmployee(2);
    }

    @Test
    void testAddEmployee() throws Exception {
        doNothing().when(employeeService).addEmployee(any(EmployeeRequest.class));

        mockMvc.perform(post("/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"empid\":1,\"empName\":\"David\",\"empType\":\"Regular\",\"empField\":\"Finance\",\"empEmail\":\"david.in@innodeed.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(AppConstants.SUCCESS));

        verify(employeeService, times(1)).addEmployee(any(EmployeeRequest.class));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        StatusResponse statusResponse = new StatusResponse(HttpStatus.OK.value(), AppConstants.SUCCESS);

        when(employeeService.updateEmployee(any(EmployeeRequest.class), eq(3))).thenReturn(statusResponse);

        mockMvc.perform(put("/employee/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"empid\":3,\"empName\":\"Eve\",\"empType\":\"Regular\",\"empField\":\"Admin\",\"empEmail\":\"eve.adam@innodeed.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(AppConstants.SUCCESS));

        verify(employeeService, times(1)).updateEmployee(any(EmployeeRequest.class), eq(3));
    }

    @Test
    void testPatchEmployee() throws Exception {
        StatusResponse statusResponse = new StatusResponse(HttpStatus.OK.value(), AppConstants.SUCCESS);

        when(employeeService.patchEmployee(any(EmployeeRequest.class), eq(4))).thenReturn(statusResponse);

        mockMvc.perform(patch("/employee/4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"empName\":\"Charlie\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(AppConstants.SUCCESS));

        verify(employeeService, times(1)).patchEmployee(any(EmployeeRequest.class), eq(4));
    }

    @Test
    void testDeleteAllEmployees() throws Exception {
        doNothing().when(employeeService).deleteAllEmployees();

        mockMvc.perform(delete("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(AppConstants.SUCCESS));

        verify(employeeService, times(1)).deleteAllEmployees();
    }

    @Test
    void testDeleteEmployeeById() throws Exception {
        doNothing().when(employeeService).deleteEmployeeByID(5);

        mockMvc.perform(delete("/employee/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(AppConstants.SUCCESS));

        verify(employeeService, times(1)).deleteEmployeeByID(5);
    }

    @Test
    void testAssignDepartments() throws Exception {
        doNothing().when(employeeService).assignDepartments(eq(6), anyList());

        mockMvc.perform(put("/employee/6/assign-departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        		  {
                        		    "departmentIds": [101, 102]
                        		  }
                        		  """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Departments assigned successfully"));

        verify(employeeService, times(1)).assignDepartments(eq(6), anyList());
    }

    @Test
    void testGetEmployeesByDepartment() throws Exception {
        EmployeeResponse response = new EmployeeResponse(7, "Frank", "Regular", "Dev", "frank@innodeed.com");

        when(employeeService.getEmployeesByDepartment(200)).thenReturn(Arrays.asList(response));

        mockMvc.perform(get("/employees/by-department/200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].empid").value(7))
                .andExpect(jsonPath("$[0].empName").value("Frank"))
                .andExpect(jsonPath("$[0].empType").value("Regular"))
                .andExpect(jsonPath("$[0].empField").value("Dev"))
                .andExpect(jsonPath("$[0].empEmail").value("frank@innodeed.com"));

        verify(employeeService, times(1)).getEmployeesByDepartment(200);
    }
}
