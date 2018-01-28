package com.eter.cake.persistence.service;

import java.util.List;

import com.eter.cake.persistence.entity.Product;
import com.eter.cake.persistence.entity.ProductCategory;
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

	List<ProductStock> getProductStock(ProductType type, String barcode);

	//List<ProductStock> getProductStock(ProductType type, String barcode, String category);

	List<ProductStock> getProductStockByGroup(ProductType type, String group);
	List<ProductStock> getProductStock(ProductType type, ProductCategory category);
	List<ProductStock> getProductStockByCategoryAndGroup(ProductType type, ProductCategory category, String group);
	List<ProductStock> getProductStockByBarcodeAndGroup(ProductType type, String barcode, String group);
	List<ProductStock> getProductStockByCategoryAndBarcode(ProductType type, ProductCategory category, String barcode);
	List<ProductStock> getProductStock(ProductType type, ProductCategory category, String barcode, String group);

	Product getByBarcode(String barcode);
	Product getByCode(String barcode);
}
