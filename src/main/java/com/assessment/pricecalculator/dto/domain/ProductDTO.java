package com.assessment.pricecalculator.dto.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import com.assessment.pricecalculator.utils.TableName;

@Entity
@Table(name = TableName.PRODUCTS)
public class ProductDTO {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(unique=true, nullable = false)
	private String name;
	
	@Column(nullable = false)
	private Double price;
	
	@Column(name = "units_per_carton", nullable = false)
	private Integer unitsPerCarton;
	
	@Column(nullable = false)
	private Double compensate;
	
	@Column(name = "discount_qty", nullable = false)
	private Integer discountQty;
	
	@Column(nullable = false)
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
