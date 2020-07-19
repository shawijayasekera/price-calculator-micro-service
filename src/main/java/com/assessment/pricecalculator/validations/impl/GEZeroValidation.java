package com.assessment.pricecalculator.validations.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.assessment.pricecalculator.validations.GEZero;

public class GEZeroValidation implements ConstraintValidator<GEZero, Double> {

	@Override
	public boolean isValid(Double value, ConstraintValidatorContext context) {

		String GE_ZERO_FORMAT = "^((\\\\d{1,}((\\\\.\\\\d{1,2})|(?!.))))$";

		if (value == null) {

			return false;
		} else {

			String stringValue = Double.toString(value);
			return stringValue.matches(GE_ZERO_FORMAT);
		}
	}
}
