package com.assessment.pricecalculator.services;

import com.assessment.pricecalculator.dto.ProductPrice;
import com.assessment.pricecalculator.dto.ProductPriceBreakdown;
import com.assessment.pricecalculator.exceptions.PriceCalculatorException;

public interface ProductPriceService {

	public ProductPrice getCalculatedProductPrice(Integer id, Integer orderQty, String type) throws PriceCalculatorException;
	
	public ProductPriceBreakdown getCalculatedProductPriceBreakdown(Integer id, Integer orderQty) throws PriceCalculatorException;
}