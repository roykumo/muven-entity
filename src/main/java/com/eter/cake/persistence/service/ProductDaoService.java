package com.eter.cake.persistence.service;

import java.util.List;

import com.eter.cake.persistence.entity.Product;
import com.eter.cake.persistence.entity.ProductType;
import com.eter.cake.persistence.entity.rest.KeyValue;
import com.eter.cake.persistence.entity.rest.ProductStock;
import com.eter.response.entity.CommonPaging;

public interface ProductDaoService {
	Product getById(String id);

	List<Product> getListPaging(int startRow, int rowPerPage, String sortDir, String sort, List<KeyValue> filter);

	int countListPaging(List<KeyValue> filter);

	Product save(Product product);

	CommonPaging<Product> getPaging(int pageSize, int page, String sortDir, String sort, List<KeyValue> filter);
	
	boolean delete(Product product);
	
	List<ProductStock> getProductStock(ProductType type);

	Product getByBarcode(String barcode);
}
