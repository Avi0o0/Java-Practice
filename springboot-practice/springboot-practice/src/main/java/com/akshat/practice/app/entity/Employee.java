package com.akshat.practice.app.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@Column(name = "empid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer empId;

	@Column(name = "empname")
	private String empName;

	@Column(name = "emptype")
	private String empType;

	@Column(name = "empfield")
	private String empField;
	
	@Column(name = "empemail")
	private String empEmail;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "employee_department", joinColumns = { @JoinColumn(name = "empid") }, 
			inverseJoinColumns = { @JoinColumn(name = "deptid") })
	@JsonManagedReference
	private Set<Department> departments = new HashSet<>();

	public Set<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	public String getEmpField() {
		return empField;
	}

	public void setEmpField(String empField) {
		this.empField = empField;
	}

	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	public Employee() {
	}

	public Employee(Integer empId, String empName, String empType, String empField, String empEmail) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.empType = empType;
		this.empField = empField;
		this.empEmail = empEmail;
	}
}
