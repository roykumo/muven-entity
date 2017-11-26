package com.eter.cake.persistence.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.eter.cake.persistence.entity.Product;
import com.eter.cake.persistence.entity.SellPrice;

public interface SellPriceRepository extends PagingAndSortingRepository<SellPrice, String>{
	SellPrice findById(String id);
	SellPrice findByIdAndActiveFlag(String id, Integer activeFlag);	
	SellPrice findFirstByActiveFlagAndProductOrderByDateDesc(Integer activeFlag, Product product);
}
