package com.assessment.pricecalculator.utils;

public enum ProductOrderType {

	UNITS("units"), CARTONS("carton");

	String tObject;

	ProductOrderType(String tObject) {

		this.tObject = tObject;
	}

	public String getTObject() {

		return this.tObject;
	}
}
