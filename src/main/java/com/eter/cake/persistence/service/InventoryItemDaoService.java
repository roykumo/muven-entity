package com.eter.cake.persistence.service;

import java.util.List;

import com.eter.cake.persistence.entity.InventoryItem;
import com.eter.cake.persistence.entity.rest.KeyValue;
import com.eter.response.entity.CommonPaging;

public interface InventoryItemDaoService {
	InventoryItem getById(String id);

	List<InventoryItem> getListPaging(int startRow, int rowPerPage, String sortDir, String sort, List<KeyValue> filter);

	int countListPaging(List<KeyValue> filter);

	InventoryItem save(InventoryItem product);

	CommonPaging<InventoryItem> getPaging(int pageSize, int page, String sortDir, String sort, List<KeyValue> filter);
}
