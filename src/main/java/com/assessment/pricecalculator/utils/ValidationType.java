package com.assessment.pricecalculator.utils;

public enum ValidationType {

	NOT_BLANK("NotBlank"), NOT_NULL("NotNull"), NOT_EMPTY("NotEmpty"), ORDER_TYPE("OrderType"), DECIMAL_MIN("DecimalMin"), MIN("Min");

	String tObject;

	ValidationType(String tObject) {

		this.tObject = tObject;
	}

	public String getTObject() {

		return this.tObject;
	}
}
