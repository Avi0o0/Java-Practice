package com.akshat.practice.app.beans.request;

import jakarta.validation.constraints.NotBlank;

public class DepartmentRequest {

	@NotBlank(message = "Department Name is required")
	private String deptName;
	
	@NotBlank(message = "Department Head is required")
	private String deptHead;

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

}
