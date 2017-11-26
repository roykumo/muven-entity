package com.eter.cake.persistence.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.eter.cake.persistence.entity.Inventory;

public interface InventoryRepository extends PagingAndSortingRepository<Inventory, String>{
	Inventory findById(String id);
	Inventory findByIdAndActiveFlag(String id, Integer activeFlag);	
}
