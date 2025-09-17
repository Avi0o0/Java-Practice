package com.practice.app.constants;

public enum AppConstants {
	SUCCESS("success"), FAILED("failed"), IN_PROGRESS("in progress");

	private final String value;

	AppConstants(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
