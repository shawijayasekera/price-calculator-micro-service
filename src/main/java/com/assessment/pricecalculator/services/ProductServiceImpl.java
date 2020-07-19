package com.assessment.pricecalculator.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.assessment.pricecalculator.dao.ProductRepository;
import com.assessment.pricecalculator.dto.Product;
import com.assessment.pricecalculator.dto.domain.ProductDTO;
import com.assessment.pricecalculator.exceptions.PriceCalculatorException;
import com.assessment.pricecalculator.exceptions.ProductNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;

	@Autowired
	public void setProductRepository(ProductRepository productRepository) {

		this.productRepository = productRepository;
	}

	@Override
	public List<Product> getAllProducts() {

		List<Product> productList = new ArrayList<>();
		List<ProductDTO> productDTOList = new ArrayList<>();
		productRepository.findAll().forEach(productDTOList::add);

		if (productDTOList.isEmpty()) {

			throw new ProductNotFoundException("No product found");
		}

		productDTOList.stream().forEach((p) -> {
			Product product = new Product();
			product.setId(p.getId());
			product.setName(p.getName());
			product.setPrice(p.getPrice());
			product.setUnitsPerCarton(p.getUnitsPerCarton());
			product.setCompensate(p.getCompensate());
			product.setDiscountQty(p.getDiscountQty());
			product.setDiscount(p.getDiscount());

			productList.add(product);
		});

		return productList;
	}

	@Override
	public Product getProduct(Integer id) {

		ProductDTO productDTO = new ProductDTO();
		Optional<ProductDTO> productDTOOptional = productRepository.findById(id);

		if (productDTOOptional.isPresent()) {

			productDTO = productDTOOptional.get();
		} else {

			throw new ProductNotFoundException("No product found for given id");
		}

		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		product.setUnitsPerCarton(productDTO.getUnitsPerCarton());
		product.setCompensate(productDTO.getCompensate());
		product.setDiscountQty(productDTO.getDiscountQty());
		product.setDiscount(productDTO.getDiscount());

		return product;
	}

	@Override
	public Product addProduct(Product product) throws PriceCalculatorException {

		ProductDTO productDTO = new ProductDTO();
		productDTO.setName(product.getName());
		productDTO.setPrice(product.getPrice());
		productDTO.setUnitsPerCarton(product.getUnitsPerCarton());
		productDTO.setCompensate(product.getCompensate());
		productDTO.setDiscountQty(product.getDiscountQty());
		productDTO.setDiscount(product.getDiscount());

		try {

			productRepository.save(productDTO);
		} catch (Exception e) {

			throw new PriceCalculatorException(e.getMessage());
		}

		product.setId(productDTO.getId());

		return product;
	}

	@Override
	public Product updateProduct(Product product) throws PriceCalculatorException {

		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setPrice(product.getPrice());
		productDTO.setUnitsPerCarton(product.getUnitsPerCarton());
		productDTO.setCompensate(product.getCompensate());
		productDTO.setDiscountQty(product.getDiscountQty());
		productDTO.setDiscount(product.getDiscount());

		try {

			productRepository.save(productDTO);
		} catch (Exception e) {

			throw new PriceCalculatorException(e.getMessage());
		}

		return product;
	}

	@Override
	public void deleteProduct(Integer id) throws PriceCalculatorException {

		try {
			
			productRepository.deleteById(id);
		} catch (Exception e) {
			
			throw new PriceCalculatorException(e.getMessage());
		}
	}
}
