package com.practice.app.request;

import java.time.LocalDateTime;

public class EmailRequest {

    private Integer id;
	
    private Integer employeeId;
	
    private Integer deptId;
	
    private String subject;
	
    private String body;
		
    private LocalDateTime sentAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public LocalDateTime getSentAt() {
		return sentAt;
	}

	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}

	public EmailRequest(Integer id, Integer employeeId, Integer deptId, String subject, String body, LocalDateTime sentAt) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.deptId = deptId;
		this.subject = subject;
		this.body = body;
		this.sentAt = sentAt;
	}

}
