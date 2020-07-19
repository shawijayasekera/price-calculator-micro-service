package com.assessment.pricecalculator.validations.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.assessment.pricecalculator.utils.ProductOrderType;
import com.assessment.pricecalculator.validations.OrderType;

public class OrderTypeValidation implements ConstraintValidator<OrderType, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value == null || value.trim().length() == 0) {

			return false;
		} else {

			if (value.equalsIgnoreCase(ProductOrderType.CARTONS.getTObject())
					|| value.equalsIgnoreCase(ProductOrderType.UNITS.getTObject())) {

				return true;
			} else {

				return false;
			}
		}
	}
}
