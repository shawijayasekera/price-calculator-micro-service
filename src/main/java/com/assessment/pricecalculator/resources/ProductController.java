package com.assessment.pricecalculator.resources;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.assessment.pricecalculator.dto.Product;
import com.assessment.pricecalculator.exceptions.PriceCalculatorException;
import com.assessment.pricecalculator.services.ProductService;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@Validated
public class ProductController {

	private ProductService productService;

	@Autowired
	public void setProductService(ProductService productService) {

		this.productService = productService;
	}

	@GetMapping("/products")
	@ApiOperation(value = "Retrieve all products", notes = "Retrieve all stored products")
	public ResponseEntity<List<Product>> getAllProducts() {

		List<Product> productList = productService.getAllProducts();

		return new ResponseEntity<>(productList, HttpStatus.OK);
	}

	@GetMapping("/products/{id}")
	@ApiOperation(value = "Retrieve product by id", notes = "Provide an id to retrieve specific product")
	public ResponseEntity<Product> getProduct(
			@PathVariable @Min(value = 1) Integer id) {

		Product product = productService.getProduct(id);

		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@PostMapping("/products")
	@ApiOperation(value = "Add product", notes = "Add new product to the product list")
	public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) throws PriceCalculatorException {

		product = productService.addProduct(product);

		return new ResponseEntity<>(product, HttpStatus.CREATED);
	}

	@PutMapping("/products/{id}")
	@ApiOperation(value = "Update product", notes = "Provide an id to update specific product")
	public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product,
			@PathVariable @Min(value = 1) Integer id)
			throws PriceCalculatorException {

		product = productService.updateProduct(product);

		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@DeleteMapping("/products/{id}")
	@ApiOperation(value = "Delete product", notes = "Provide an id to delete specific product")
	public ResponseEntity<?> deleteProduct(
			@PathVariable @Min(value = 1) Integer id)
			throws PriceCalculatorException {

		productService.deleteProduct(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
