package com.eter.cake.persistence.repo;

import com.eter.cake.persistence.entity.InventoryItemOut;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InventoryItemOutRepository extends PagingAndSortingRepository<InventoryItemOut, String>{
	InventoryItemOut findById(String id);
	InventoryItemOut findByIdAndActiveFlag(String id, Integer activeFlag);
}
