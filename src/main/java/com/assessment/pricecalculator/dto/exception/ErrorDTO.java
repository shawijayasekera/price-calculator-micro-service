package com.assessment.pricecalculator.dto.exception;

public class ErrorDTO {

	private String errorField;
	private String rejectedValue;

	public String getErrorField() {
		return errorField;
	}

	public void setErrorField(String errorField) {
		this.errorField = errorField;
	}

	public String getRejectedValue() {
		return rejectedValue;
	}

	public void setRejectedValue(String rejectedValue) {
		this.rejectedValue = rejectedValue;
	}
}
