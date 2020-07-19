package com.assessment.pricecalculator.dto;

import java.util.List;

public class ProductPriceBreakdown {

	private Product product;
	private List<QuantityPrice> quantityPrice;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<QuantityPrice> getQuantityPrice() {
		return quantityPrice;
	}

	public void setQuantityPrice(List<QuantityPrice> quantityPrice) {
		this.quantityPrice = quantityPrice;
	}
}
