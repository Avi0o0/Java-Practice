package com.practice.app.response;

public class StatusResponse {

	private Integer id;
	
	private Integer status;
	
	private String message;
	
	private String emailStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}

	public StatusResponse(Integer id, Integer status, String message, String emailStatus) {
		super();
		this.id = id;
		this.status = status;
		this.message = message;
		this.emailStatus = emailStatus;
	}

	public StatusResponse() {
		super();
	}
	
}
