package com.assessment.pricecalculator.utils;

public enum ServiceErrorType {

	SVC_0001("SVC0001", "A service error occurred. Error code is %1"),
	SVC_0002("SVC0002", "Invalid input value for message part %1");

	private String errorCode;
	private String errorMessage;

	private ServiceErrorType(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
