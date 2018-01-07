package com.eter.cake.persistence.repo;

import com.eter.cake.persistence.entity.ProductCategory;
import com.eter.cake.persistence.entity.ProductType;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductCategoryRepository extends PagingAndSortingRepository<ProductCategory, String>{
	ProductCategory findById(String id);
	ProductCategory findByIdAndActiveFlag(String id, Integer activeFlag);
}
