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
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Should return departments for given employeeId")
    void testFindDepartmentsByEmployeeId() {
        // given
        Department dept1 = new Department(null, "IT", "Alice");
        Department dept2 = new Department(null, "HR", "Bob");

        Employee emp1 = new Employee();
        emp1.setEmpName("John");
        emp1.setEmpEmail("john@example.com");
        emp1.setEmpType("Full-Time");
        emp1.setEmpField("Development");

        Set<Department> empDepts = new HashSet<>();
        empDepts.add(dept1);
        empDepts.add(dept2);
        emp1.setDepartments(empDepts);

        dept1.getEmployees().add(emp1);
        dept2.getEmployees().add(emp1);

        departmentRepository.save(dept1);
        departmentRepository.save(dept2);
        employeeRepository.save(emp1);

        // when
        List<Department> result = departmentRepository.findDepartmentsByEmployeeId(emp1.getEmpId());

        // then
        assertThat(result).hasSize(2);
        assertThat(result).extracting("deptName").containsExactlyInAnyOrder("IT", "HR");
    }

    @Test
    @DisplayName("Should return empty list when employee has no departments")
    void testFindDepartmentsByEmployeeId_NoDepartments() {
        // given
        Employee emp2 = new Employee();
        emp2.setEmpName("Jane");
        emp2.setEmpEmail("jane@example.com");
        emp2.setEmpType("Part-Time");
        emp2.setEmpField("Support");

        employeeRepository.save(emp2);

        // when
        List<Department> result = departmentRepository.findDepartmentsByEmployeeId(emp2.getEmpId());

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return empty list when employee does not exist")
    void testFindDepartmentsByEmployeeId_EmployeeNotFound() {
        // when
        List<Department> result = departmentRepository.findDepartmentsByEmployeeId(999);

        // then
        assertThat(result).isEmpty();
    }
}
