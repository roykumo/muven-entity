package com.eter.cake.persistence.service;

import java.util.List;

import com.eter.cake.persistence.entity.Product;
import com.eter.cake.persistence.entity.SellPrice;


public interface SellPriceDaoService extends BaseService{
	SellPrice getLatestPriceByProduct(Product product);
	
	List<SellPrice> getSellPricesByProducts(List<Product> products);
}
