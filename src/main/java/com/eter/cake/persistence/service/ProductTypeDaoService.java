package com.eter.cake.persistence.service;

import java.util.List;

import com.eter.cake.persistence.entity.ProductType;
import com.eter.cake.persistence.entity.rest.KeyValue;
import com.eter.response.entity.CommonPaging;

public interface ProductTypeDaoService {
	ProductType getById(String id);

	List<ProductType> getListPaging(int startRow, int rowPerPage, String sortDir, String sort, List<KeyValue> filter);

	int countListPaging(List<KeyValue> filter);

	ProductType save(ProductType productType);

	CommonPaging<ProductType> getPaging(int pageSize, int page, String sortDir, String sort, List<KeyValue> filter);
}
