package com.practice.app.request;

public class EmailRequest {

    private Integer employeeId;
	
    private Integer deptId;
	
    private String subject;
	
    private String body;
    
    private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public EmailRequest(Integer employeeId, Integer deptId, String subject, String body) {
		super();
		this.employeeId = employeeId;
		this.deptId = deptId;
		this.subject = subject;
		this.body = body;
	}
}
