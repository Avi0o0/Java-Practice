package com.akshat.practice.app.beans;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@Column(name = "empid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int empId;

	@Column(name = "empname")
	private String empName;

	@Column(name = "emptype")
	private String empType;
	
	@Column(name = "empfield")
	private String empField;

	
	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
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
	
	public Employee() {}

	public Employee(int empId, String empName, String empType, String empField) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.empType = empType;
		this.empField = empField;
	}
}
