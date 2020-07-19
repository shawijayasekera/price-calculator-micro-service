package com.assessment.pricecalculator.resources;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.assessment.pricecalculator.dto.ProductPrice;
import com.assessment.pricecalculator.dto.ProductPriceBreakdown;
import com.assessment.pricecalculator.exceptions.PriceCalculatorException;
import com.assessment.pricecalculator.services.ProductPriceService;
import com.assessment.pricecalculator.validations.OrderType;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@Validated
public class ProductPriceController {

	private ProductPriceService productPriceService;

	@Autowired
	public void setProductPriceService(ProductPriceService productPriceService) {
		this.productPriceService = productPriceService;
	}

	@GetMapping("/products/{id}/price")
	@ApiOperation(value = "Retrieve calculated product price", notes = "Retrieve calculated product price according to the given quantity and type")
	public ResponseEntity<ProductPrice> getCalculatedProductPrice(@PathVariable @Min(value = 1) Integer id,
			@RequestParam @NotNull(message = "qty") @Min(value = 1) Integer qty,
			@RequestParam @NotBlank(message = "orderType") @OrderType(message = "orderType") String orderType)
			throws PriceCalculatorException {

		ProductPrice productPrice = productPriceService.getCalculatedProductPrice(id, qty, orderType);

		return new ResponseEntity<>(productPrice, HttpStatus.OK);
	}

	@GetMapping("/products/{id}/price/breakdown")
	@ApiOperation(value = "Retrieve calculated product price breakdown", notes = "Retrieve calculated product price breakdown according to the given quantity")
	public ResponseEntity<ProductPriceBreakdown> getCalculatedProductPrice(@PathVariable @Min(value = 1) Integer id,
			@RequestParam @NotNull(message = "qty") @Min(value = 1) Integer qty) throws PriceCalculatorException {

		ProductPriceBreakdown productPriceBreakdown = productPriceService.getCalculatedProductPriceBreakdown(id, qty);

		return new ResponseEntity<>(productPriceBreakdown, HttpStatus.OK);
	}
}
