package com.eter.cake.persistence.service;

import com.eter.cake.persistence.entity.InventoryItemOut;
import com.eter.cake.persistence.entity.rest.KeyValue;
import com.eter.response.entity.CommonPaging;

import java.util.List;

public interface InventoryItemOutDaoService {
	InventoryItemOut getById(String id);

	List<InventoryItemOut> getListPaging(int startRow, int rowPerPage, String sortDir, String sort, List<KeyValue> filter);

	int countListPaging(List<KeyValue> filter);

	InventoryItemOut save(InventoryItemOut product);

	CommonPaging<InventoryItemOut> getPaging(int pageSize, int page, String sortDir, String sort, List<KeyValue> filter);
}
