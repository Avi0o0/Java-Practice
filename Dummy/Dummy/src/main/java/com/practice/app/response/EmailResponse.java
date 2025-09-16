package com.practice.app.response;

import java.time.LocalDateTime;

import com.practice.app.request.EmailRequest;

public class EmailResponse {

    private Integer id;
	
    private Integer employeeId;
	
    private String subject;
	
    private String body;
	
    private String status;
	
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

	public LocalDateTime getSentAt() {
		return sentAt;
	}

	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}

	public EmailResponse(Integer id, Integer employeeId, String subject, String body, String status, LocalDateTime sentAt) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.subject = subject;
		this.body = body;
		this.status = status;
		this.sentAt = sentAt;
	}

	public EmailResponse(EmailRequest emailRequest, String status, LocalDateTime sentAt) {
		this.id = emailRequest.getId();
		this.employeeId = emailRequest.getEmployeeId();
		this.subject = emailRequest.getSubject();
		this.body = emailRequest.getBody();
		this.status = status;
		this.sentAt = sentAt;
	}

}
