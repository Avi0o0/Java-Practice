package com.akshat.practice.app.beans.request;

import com.akshat.practice.app.customannotations.InnodeedEmail;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class EmployeeRequest {
	
	private Integer empid;
	
	@NotBlank(message = "Name is required")
	private String empName;
	
	@NotBlank(message = "Employee Type is required")
	@Pattern(regexp = "Regular|Contractual", message = "Type must be Regular or Contractual")
	private String empType;
	
	@NotBlank(message = "Employee Field is required")
	private String empField;
	
	@NotBlank(message = "Email cannot be blank")
	@InnodeedEmail
	private String empEmail;

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

	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	// Constructor
	public EmployeeRequest(Integer empid, String empName, String empType, String empField, String empEmail) {
		super();
		this.empid = empid;
		this.empName = empName;
		this.empType = empType;
		this.empField = empField;
		this.empEmail = empEmail;
	}
	
}
