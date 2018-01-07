package com.eter.cake.persistence.service;

import com.eter.cake.persistence.entity.ProductCategory;
import com.eter.cake.persistence.entity.rest.KeyValue;
import com.eter.response.entity.CommonPaging;

import java.util.List;

public interface ProductCategoryDaoService {
	ProductCategory getById(String id);

	List<ProductCategory> getListPaging(int startRow, int rowPerPage, String sortDir, String sort, List<KeyValue> filter);

	int countListPaging(List<KeyValue> filter);

	ProductCategory save(ProductCategory productCategory);

	CommonPaging<ProductCategory> getPaging(int pageSize, int page, String sortDir, String sort, List<KeyValue> filter);
}
