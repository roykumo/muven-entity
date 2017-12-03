package com.eter.cake.persistence.repo;

import com.eter.cake.persistence.entity.InventoryOut;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InventoryOutRepository extends PagingAndSortingRepository<InventoryOut, String>{
	InventoryOut findById(String id);
	InventoryOut findByIdAndActiveFlag(String id, Integer activeFlag);
}
