package com.eter.cake.persistence.entity.rest;

import java.math.BigDecimal;
import java.util.Date;

import com.eter.cake.persistence.entity.Product;
import com.eter.cake.persistence.entity.SellPrice;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ProductStock {
	private Product product;
	private int quantity;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="GMT+7")
	private Date expiredDate;
	private BigDecimal buyPrice;
	private SellPrice sellPrice;
	
	public ProductStock(){}
	
	public ProductStock(Product product, long quantity, BigDecimal buyPrice, Date expiredDate){
		this.product = product;
		this.quantity = Integer.parseInt(String.valueOf(quantity));
		this.buyPrice = buyPrice;
		this.expiredDate = expiredDate;
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Date getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	public BigDecimal getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}
	public SellPrice getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(SellPrice sellPrice) {
		this.sellPrice = sellPrice;
	}
}
