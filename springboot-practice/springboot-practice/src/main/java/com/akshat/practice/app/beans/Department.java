package com.akshat.practice.app.beans;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import com.akshat.practice.app.beans.request.DepartmentRequest;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "department")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "deptid")
	private Integer deptId;

	@Column(name = "deptname")
	private String deptName;

	@Column(name = "depthead")
	private String deptHead;

	@ManyToMany(mappedBy = "departments")
	@JsonBackReference
	private Set<Employee> employees = new HashSet<>();

	public Department() {
	}

	public Department(Integer deptId, String deptName, String deptHead) {
		this.deptId = deptId;
		this.deptName = deptName;
		this.deptHead = deptHead;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptHead() {
		return deptHead;
	}

	public void setDeptHead(String deptHead) {
		this.deptHead = deptHead;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	public Department(DepartmentRequest departmentRequest) {
		this.deptName = departmentRequest.getDeptName();
		this.deptHead = departmentRequest.getDeptHead();
	}
	
	

}
