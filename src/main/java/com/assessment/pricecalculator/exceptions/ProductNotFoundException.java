package com.assessment.pricecalculator.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1489004975713257432L;

	public ProductNotFoundException(String message) {

		super(message);
	}
}
