package com.akshat.practice.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.akshat.practice.app.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    @Query("SELECT d FROM Department d JOIN d.employees e WHERE e.empId = :empId")
    List<Department> findDepartmentsByEmployeeId(@Param("empId") Integer empId);
}
