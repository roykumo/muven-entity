package com.eter.cake.persistence.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.eter.cake.persistence.entity.ProductType;

public interface ProductTypeRepository extends PagingAndSortingRepository<ProductType, String>{
	ProductType findById(String id);
	ProductType findByIdAndActiveFlag(String id, Integer activeFlag);	
}
