package com.akshat.practice.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akshat.practice.app.beans.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT e FROM Employee e JOIN e.departments d WHERE d.deptId = :deptId")
    List<Employee> findEmployeesByDepartmentId(@Param("deptId") Integer deptId);
}