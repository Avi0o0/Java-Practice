package com.akshat.practice.app.repository;

import com.akshat.practice.app.entity.Department;
import com.akshat.practice.app.entity.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    @DisplayName("Should save employee and check email existence")
    void testExistsByEmpEmail() {
        Employee emp = new Employee(null, "Akshat", "FullTime", "IT", "akshat@example.com");

        employeeRepository.save(emp);

        boolean exists = employeeRepository.existsByEmpEmail("akshat@example.com");
        boolean notExists = employeeRepository.existsByEmpEmail("random@example.com");

        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    @DisplayName("Should find employees by department id")
    void testFindEmployeesByDepartmentId() {
        Department dept = new Department();
        dept.setDeptName("Engineering");
        dept.setDeptHead("Head1");

        departmentRepository.save(dept);

        Employee emp1 = new Employee(null, "John", "FullTime", "IT", "john@example.com");
        Employee emp2 = new Employee(null, "Jane", "PartTime", "HR", "jane@example.com");

        Set<Department> departments = new HashSet<>();
        departments.add(dept);

        emp1.setDepartments(departments);
        emp2.setDepartments(departments);

        employeeRepository.save(emp1);
        employeeRepository.save(emp2);

        List<Employee> employees = employeeRepository.findEmployeesByDepartmentId(dept.getDeptId());

        assertThat(employees).hasSize(2);
        assertThat(employees).extracting(Employee::getEmpName).contains("John", "Jane");
    }
}
