package com.assessment.pricecalculator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PriceCalculatorException extends Exception {

	private static final long serialVersionUID = 5571527670124137068L;

	public PriceCalculatorException(String message) {

		super(message);
	}
}
