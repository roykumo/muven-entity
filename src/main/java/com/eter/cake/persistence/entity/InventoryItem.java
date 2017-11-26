package com.eter.cake.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name="InventoryItemEntity")
@Table(name = "inventory_item")
@JsonFilter(value="jsonFilterInventoryItemEntity")
public class InventoryItem extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 7764179686719330808L;

	public abstract interface Constant{
		public static final String ID_FIELD = "id";
		public static final String PRODUCT_FIELD = "product";
		public static final String EXPIRED_DATE_FIELD = "expiredDate";
		public static final String CREATED_DATE_FIELD = "createdDate";
		public static final String MODIFIED_DATE_FIELD = "modifiedDate";
	}
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@ManyToOne
    @JoinColumn(name="product", nullable=false)
    private Product product;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone="GMT+7")
    private Date expiredDate;
    private Integer quantity;
    private BigDecimal purchasePrice;
    private String remarks;
    
    @ManyToOne
    @JoinColumn(name="inventory", nullable=false)
    @JsonIgnoreProperties({"items"})
    private Inventory inventory;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Date getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}
	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
}
