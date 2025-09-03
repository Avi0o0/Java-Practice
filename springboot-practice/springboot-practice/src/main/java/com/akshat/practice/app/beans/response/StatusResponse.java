package com.akshat.practice.app.beans.response;

public class StatusResponse {

	private int status;
	private String message;

	// Getters Setters
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public StatusResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public StatusResponse() {
	}
}
