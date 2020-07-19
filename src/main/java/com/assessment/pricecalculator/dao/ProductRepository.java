package com.assessment.pricecalculator.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assessment.pricecalculator.dto.domain.ProductDTO;

@Repository
public interface ProductRepository extends CrudRepository<ProductDTO, Integer> {

}
