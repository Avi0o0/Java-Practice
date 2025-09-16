package com.practice.app.entity;

import java.time.LocalDateTime;

import com.practice.app.request.EmailRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "email_data")
public class EmailEntity {
   
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
    private Integer id;
	
	@Column(name = "empid")
    private Integer employeeId;
	
	@Column(name = "deptid")
    private Integer deptId;
	
	@Column(name = "subject")
    private String subject;
	
	@Column(name = "body")
    private String body;
	
	@Column(name = "status")
    private String status;
	
	@Column(name = "sentat")
    private LocalDateTime sentAt;

	public EmailEntity(Integer employeeId, Integer deptId, String subject, String body, String status,
			LocalDateTime sentAt) {
		super();
		this.employeeId = employeeId;
		this.deptId = deptId;
		this.subject = subject;
		this.body = body;
		this.status = status;
		this.sentAt = sentAt;
	}

	public EmailEntity() {
		super();
	}

	public EmailEntity(EmailRequest emailRequest, String status, LocalDateTime sentAt) {
		this.employeeId = emailRequest.getEmployeeId();
		this.deptId = emailRequest.getDeptId();
		this.subject = emailRequest.getSubject();
		this.body = emailRequest.getBody();
		this.status = status;
		this.sentAt = sentAt;
	}

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
}
