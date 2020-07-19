package com.assessment.pricecalculator.dto;

public class ProductPrice {

	private Product product;
	private String orderType;
	private QuantityPrice quantityPrice;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public QuantityPrice getQuantityPrice() {
		return quantityPrice;
	}

	public void setQuantityPrice(QuantityPrice quantityPrice) {
		this.quantityPrice = quantityPrice;
	}
}
