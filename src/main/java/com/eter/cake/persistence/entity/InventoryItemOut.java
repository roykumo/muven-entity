package com.eter.cake.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity(name="InventoryItemOutEntity")
@Table(name = "inventory_item_out")
@JsonFilter(value="jsonFilterInventoryItemOutEntity")
public class InventoryItemOut extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1;

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
    private Integer quantity;
    private BigDecimal purchasePrice;
	private BigDecimal sellPriceTrx;
	private String remarks;

	@ManyToOne
	@JoinColumn(name="sell_price")
	private SellPrice sellPrice;

	@ManyToOne
	@JoinColumn(name="inventory_out", nullable=false)
	@JsonIgnoreProperties({"items"})
	private InventoryOut inventoryOut;

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
	public SellPrice getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(SellPrice sellPrice) {
		this.sellPrice = sellPrice;
	}
	public InventoryOut getInventoryOut() {
		return inventoryOut;
	}
	public void setInventoryOut(InventoryOut inventoryOut) {
		this.inventoryOut = inventoryOut;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public BigDecimal getSellPriceTrx() {
		return sellPriceTrx;
	}
	public void setSellPriceTrx(BigDecimal sellPriceTrx) {
		this.sellPriceTrx = sellPriceTrx;
	}
}
