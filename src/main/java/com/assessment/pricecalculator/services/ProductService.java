package com.assessment.pricecalculator.services;

import java.util.List;
import com.assessment.pricecalculator.dto.Product;
import com.assessment.pricecalculator.exceptions.PriceCalculatorException;

public interface ProductService {

	public List<Product> getAllProducts();

	public Product getProduct(Integer id);

	public Product addProduct(Product product) throws PriceCalculatorException;

	public Product updateProduct(Product product) throws PriceCalculatorException;

	public void deleteProduct(Integer id) throws PriceCalculatorException;
}