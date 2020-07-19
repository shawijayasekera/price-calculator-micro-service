package com.assessment.pricecalculator.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Product {

	@Min(value = 1, message = "id must be equal or greater than 1")
	private Integer id;

	@NotBlank(message = "name")
	private String name;

	@NotNull(message = "price")
	@DecimalMin(value = "1.00", message = "price must be equal or greater than 1.00")
	private Double price;

	@NotNull(message = "unitsPerCarton")
	@Min(value = 1, message = "unitsPerCarton must be equal or greater than 1")
	private Integer unitsPerCarton;

	@NotNull(message = "compensate")
	@DecimalMin(value = "0.01", message = "compensate must be equal or greater than 0.01")
	private Double compensate;

	@NotNull(message = "discountQty")
	@Min(value = 1, message = "discountQty must be equal or greater than 1")
	private Integer discountQty;

	@NotNull(message = "discount")
	@DecimalMin(value = "0.01", message = "discount must be equal or greater than 0.01")
	private Double discount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getUnitsPerCarton() {
		return unitsPerCarton;
	}

	public void setUnitsPerCarton(Integer unitsPerCarton) {
		this.unitsPerCarton = unitsPerCarton;
	}

	public Double getCompensate() {
		return compensate;
	}

	public void setCompensate(Double compensate) {
		this.compensate = compensate;
	}

	public Integer getDiscountQty() {
		return discountQty;
	}

	public void setDiscountQty(Integer discountQty) {
		this.discountQty = discountQty;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
}
