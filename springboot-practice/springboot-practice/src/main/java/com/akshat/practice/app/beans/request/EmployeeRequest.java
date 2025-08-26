package com.akshat.practice.app.beans.request;

public class EmployeeRequest {

	private int empid;
	private String empName;
	private String empType;
	private String empField;
	
	//Getters Setters
	public int getEmpid() {
		return empid;
	}
	public void setEmpid(int empid) {
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
	
	//Constructor
	public EmployeeRequest(int empid, String empName, String empType, String empField) {
		super();
		this.empid = empid;
		this.empName = empName;
		this.empType = empType;
		this.empField = empField;
	}
}
