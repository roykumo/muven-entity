package com.eter.cake.persistence.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.eter.cake.persistence.entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, String>{
	Product findById(String id);
	Product findByIdAndActiveFlag(String id, Integer activeFlag);
	Product findByBarcodeAndActiveFlag(String barcode, Integer activeFlag);
}
