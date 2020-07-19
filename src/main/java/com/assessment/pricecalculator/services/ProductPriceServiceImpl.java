package com.assessment.pricecalculator.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.assessment.pricecalculator.dto.Product;
import com.assessment.pricecalculator.dto.ProductPrice;
import com.assessment.pricecalculator.dto.ProductPriceBreakdown;
import com.assessment.pricecalculator.dto.QuantityPrice;
import com.assessment.pricecalculator.exceptions.PriceCalculatorException;

@Service
public class ProductPriceServiceImpl implements ProductPriceService {

	private ProductService productService;

	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@Override
	public ProductPrice getCalculatedProductPrice(Integer id, Integer orderQty, String type)
			throws PriceCalculatorException {

		ProductPrice productPrice = new ProductPrice();
		QuantityPrice quantityPrice = new QuantityPrice();

		try {
			Product product = productService.getProduct(id);
			double totalPrice;

			if (type.equals("units")) {

				int noOfCartons = orderQty / product.getUnitsPerCarton();
				double cartonsPrice = calculateCartonsPrice(product, noOfCartons);

				double singleUnitsPrice = calculateSingleUnitsPrice(product, orderQty);

				totalPrice = cartonsPrice + singleUnitsPrice;
			} else {

				totalPrice = calculateCartonsPrice(product, orderQty);
			}

			totalPrice = Math.round(totalPrice * 100.0) / 100.0;
			quantityPrice.setOrderQty(orderQty);
			quantityPrice.setCalculatedPrice(totalPrice);
			
			productPrice.setProduct(product);
			productPrice.setOrderType(type);
			productPrice.setQuantityPrice(quantityPrice);
		} catch (Exception e) {

			throw new PriceCalculatorException(e.getMessage());
		}

		return productPrice;
	}

	public ProductPriceBreakdown getCalculatedProductPriceBreakdown(Integer id, Integer orderQty)
			throws PriceCalculatorException {

		ProductPriceBreakdown productPriceBreakdown = new ProductPriceBreakdown();

		try {
			Product product = productService.getProduct(id);
			productPriceBreakdown.setProduct(product);

			List<QuantityPrice> quantityPriceList = new ArrayList<>();

			IntStream.rangeClosed(1, orderQty).forEach(qty -> {

				QuantityPrice quantityPrice = new QuantityPrice();
				
				int noOfCartons = qty / product.getUnitsPerCarton();
				double cartonsPrice = calculateCartonsPrice(product, noOfCartons);

				double singleUnitsPrice = calculateSingleUnitsPrice(product, qty);

				double totalPrice = cartonsPrice + singleUnitsPrice;
				totalPrice = Math.round(totalPrice * 100.0) / 100.0;
				
				quantityPrice.setOrderQty(qty);
				quantityPrice.setCalculatedPrice(totalPrice);
				
				quantityPriceList.add(quantityPrice);
			});
			
			productPriceBreakdown.setQuantityPrice(quantityPriceList);
		} catch (Exception e) {

			throw new PriceCalculatorException(e.getMessage());
		}

		return productPriceBreakdown;
	}

	private double calculateCartonsPrice(Product product, int noOfCartons) {

		double cartonsPrice = noOfCartons * product.getPrice();

		if (noOfCartons >= product.getDiscountQty()) {

			cartonsPrice = cartonsPrice * product.getDiscount();
		}

		return cartonsPrice;
	}

	private double calculateSingleUnitsPrice(Product product, int orderQty) {

		int noOfSingleUnits = orderQty % product.getUnitsPerCarton();
		double pricePerUnit = product.getPrice() / product.getUnitsPerCarton();

		return (noOfSingleUnits * pricePerUnit) * product.getCompensate();
	}
}
