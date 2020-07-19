package com.assessment.pricecalculator.services;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.assessment.pricecalculator.dto.Product;
import com.assessment.pricecalculator.dto.ProductPrice;
import com.assessment.pricecalculator.dto.ProductPriceBreakdown;
import com.assessment.pricecalculator.dto.QuantityPrice;
import com.assessment.pricecalculator.exceptions.PriceCalculatorException;

@SpringBootTest
class ProductPriceServiceImplTest {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductPriceService productPriceService;

	private Product testProductOne;
	private Product testProductTwo;

	@BeforeEach
	void setUp() throws Exception {

		testProductOne = new Product();
		testProductOne.setName("JunitTestProduct-1");
		testProductOne.setPrice(500.00);
		testProductOne.setUnitsPerCarton(25);
		testProductOne.setCompensate(1.5);
		testProductOne.setDiscountQty(10);
		testProductOne.setDiscount(0.8);

		testProductOne = productService.addProduct(testProductOne);
	}

	@AfterEach
	void tearDown() throws Exception {

		productService.deleteProduct(testProductOne.getId());
	}

	@Test
	void testGetCalculatedProductPriceForCarton() throws PriceCalculatorException {

		ProductPrice productPrice = productPriceService.getCalculatedProductPrice(testProductOne.getId(), 5, "carton");
		assertEquals(2500, productPrice.getQuantityPrice().getCalculatedPrice());
	}

	@Test
	void testGetCalculatedProductPriceForCartonWithDiscountEqualQuantity() throws PriceCalculatorException {

		ProductPrice productPrice = productPriceService.getCalculatedProductPrice(testProductOne.getId(), 10, "carton");
		assertEquals(4000, productPrice.getQuantityPrice().getCalculatedPrice());
	}

	@Test
	void testGetCalculatedProductPriceForCartonWithGraterThanDiscountQuantity() throws PriceCalculatorException {

		ProductPrice productPrice = productPriceService.getCalculatedProductPrice(testProductOne.getId(), 11, "carton");
		assertEquals(4400, productPrice.getQuantityPrice().getCalculatedPrice());
	}

	@Test
	void testGetCalculatedProductPriceForCartonWithLessThanDiscountQuantity() throws PriceCalculatorException {

		ProductPrice productPrice = productPriceService.getCalculatedProductPrice(testProductOne.getId(), 9, "carton");
		assertEquals(4500, productPrice.getQuantityPrice().getCalculatedPrice());
	}

	@Test
	void testGetCalculatedProductPriceForUnits() throws PriceCalculatorException {

		ProductPrice productPrice = productPriceService.getCalculatedProductPrice(testProductOne.getId(), 10, "units");
		assertEquals(300, productPrice.getQuantityPrice().getCalculatedPrice());
	}

	@Test
	void testGetCalculatedProductPriceForUnitsEqualsToCarton() throws PriceCalculatorException {

		ProductPrice productPrice = productPriceService.getCalculatedProductPrice(testProductOne.getId(), 25, "units");
		assertEquals(500, productPrice.getQuantityPrice().getCalculatedPrice());
	}

	@Test
	void testGetCalculatedProductPriceForUnitsGraterThanToCarton() throws PriceCalculatorException {

		ProductPrice productPrice = productPriceService.getCalculatedProductPrice(testProductOne.getId(), 35, "units");
		assertEquals(800, productPrice.getQuantityPrice().getCalculatedPrice());
	}

	@Test
	void testGetCalculatedProductPriceForUnitsEqualsTODiscountQuantity() throws PriceCalculatorException {

		ProductPrice productPrice = productPriceService.getCalculatedProductPrice(testProductOne.getId(), 250, "units");
		assertEquals(4000, productPrice.getQuantityPrice().getCalculatedPrice());
	}

	@Test
	void testGetCalculatedProductPriceForUnitsWithDiscountQuantityAndCompensate() throws PriceCalculatorException {

		ProductPrice productPrice = productPriceService.getCalculatedProductPrice(testProductOne.getId(), 260, "units");
		assertEquals(4300, productPrice.getQuantityPrice().getCalculatedPrice());
	}

	@Test
	void testGetCalculatedProductPriceBreakdownForSixUnits() throws PriceCalculatorException {

		testProductTwo = new Product();
		testProductTwo.setName("JunitTestProduct-2");
		testProductTwo.setPrice(100.00);
		testProductTwo.setUnitsPerCarton(5);
		testProductTwo.setCompensate(1.5);
		testProductTwo.setDiscountQty(2);
		testProductTwo.setDiscount(0.8);

		testProductTwo = productService.addProduct(testProductTwo);

		ProductPriceBreakdown productPriceBreakdown = productPriceService
				.getCalculatedProductPriceBreakdown(testProductTwo.getId(), 6);

		Double[] testPrices = { 30.00, 60.00, 90.00, 120.00, 100.00, 130.00 };
		int i = 0;

		for (QuantityPrice quantityPrice : productPriceBreakdown.getQuantityPrice()) {

			assertEquals(testPrices[i], quantityPrice.getCalculatedPrice());
			i++;
		}

		productService.deleteProduct(testProductTwo.getId());
	}
}
