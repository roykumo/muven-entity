package com.eter.cake.persistence.service;

import java.util.List;

import com.eter.cake.persistence.entity.Inventory;
import com.eter.cake.persistence.entity.rest.KeyValue;
import com.eter.response.entity.CommonPaging;

public interface InventoryDaoService {
	Inventory getById(String id);

	List<Inventory> getListPaging(int startRow, int rowPerPage, String sortDir, String sort, List<KeyValue> filter);

	int countListPaging(List<KeyValue> filter);

	Inventory save(Inventory product) throws Exception;

	CommonPaging<Inventory> getPaging(int pageSize, int page, String sortDir, String sort, List<KeyValue> filter);
}
