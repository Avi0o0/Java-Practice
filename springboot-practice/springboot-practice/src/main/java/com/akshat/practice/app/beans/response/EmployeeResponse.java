package com.akshat.practice.app.beans.response;

public class EmployeeResponse {

	private Integer empid;
	private String empName;
	private String empType;
	private String empField;

	// Getters Setters
	public Integer getEmpid() {
		return empid;
	}

	public void setEmpid(Integer empid) {
		this.empid = empid;
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

	// Constructor
	public EmployeeResponse(Integer empid, String empName, String empType, String empField) {
		super();
		this.empid = empid;
		this.empName = empName;
		this.empType = empType;
		this.empField = empField;
	}
}
