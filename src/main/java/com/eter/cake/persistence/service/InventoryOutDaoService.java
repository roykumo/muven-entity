package com.eter.cake.persistence.service;

import com.eter.cake.persistence.entity.InventoryOut;
import com.eter.cake.persistence.entity.rest.KeyValue;
import com.eter.response.entity.CommonPaging;

import java.util.List;

public interface InventoryOutDaoService {
	InventoryOut getById(String id);

	List<InventoryOut> getListPaging(int startRow, int rowPerPage, String sortDir, String sort, List<KeyValue> filter);

	int countListPaging(List<KeyValue> filter);

	InventoryOut save(InventoryOut product) throws Exception;

	CommonPaging<InventoryOut> getPaging(int pageSize, int page, String sortDir, String sort, List<KeyValue> filter);
}
