package com.eter.cake.persistence.service;

import com.eter.cake.persistence.entity.Inventory;
import com.eter.cake.persistence.entity.Product;
import com.eter.cake.persistence.entity.ProductType;
import com.eter.cake.persistence.entity.rest.KeyValue;
import com.eter.cake.persistence.entity.rest.SellingReport;
import com.eter.cake.persistence.entity.rest.Transaction;
import com.eter.response.entity.CommonPaging;

import java.util.List;

public interface TransactionDaoService {
	CommonPaging<Transaction> getTransactions(int pageSize, int page, String sortDir, String sort, List<KeyValue> filter);
	List<SellingReport> getSellingReport(ProductType type, int month, int year);
}
