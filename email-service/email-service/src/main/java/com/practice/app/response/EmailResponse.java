package com.practice.app.response;

import java.time.LocalDateTime;

import com.practice.app.entity.EmailEntity;
import com.practice.app.request.EmailRequest;

public class EmailResponse {
	
    private Integer employeeId;
	
    private String subject;
	
    private String body;
	
    private String status;
	
    private LocalDateTime time;

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public EmailResponse(Integer employeeId, String subject, String body, String status, LocalDateTime time) {
		super();
		this.employeeId = employeeId;
		this.subject = subject;
		this.body = body;
		this.status = status;
		this.time = time;
	}

	public EmailResponse(EmailRequest emailRequest, String status, LocalDateTime time) {
		this.employeeId = emailRequest.getEmployeeId();
		this.subject = emailRequest.getSubject();
		this.body = emailRequest.getBody();
		this.status = status;
		this.time = time;
	}
	
	public EmailResponse(EmailEntity email) {
		this.employeeId = email.getEmployeeId();
		this.subject = email.getSubject();
		this.body = email.getBody();
		this.status = email.getStatus();
		this.time = email.getTime();
	}

}
