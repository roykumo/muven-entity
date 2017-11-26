package com.eter.cake.persistence.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.eter.cake.persistence.entity.InventoryItem;

public interface InventoryItemRepository extends PagingAndSortingRepository<InventoryItem, String>{
	InventoryItem findById(String id);
	InventoryItem findByIdAndActiveFlag(String id, Integer activeFlag);	
}
