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

@Entity(name="SellPriceEntity")
@Table(name = "sell_price")
@JsonFilter(value="jsonFilterSellPriceEntity")
public class SellPrice extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -9051169886520781044L;

	public abstract interface Constant{
		public static final String ID_FIELD = "id";
		public static final String PRODUCT_FIELD = "product";
		public static final String DATE_FIELD = "date";
		public static final String BUY_PRICE_FIELD = "buyPrice";
		public static final String SELLING_PRICE_FIELD = "sellingPrice";
		public static final String REMARKS_FIELD = "remarks";
		public static final String SALE_FIELD = "sale";
		public static final String CREATED_DATE_FIELD = "createdDate";
		public static final String MODIFIED_DATE_FIELD = "modifiedDate";
	}
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone="GMT+7")
	private Date date;
	private BigDecimal buyPrice;
	private BigDecimal sellingPrice;
	private String remarks;
	private Boolean sale;
	
	@ManyToOne
    @JoinColumn(name="product", nullable=false)
    private Product product;
	
	public SellPrice(){
		
	}
	
	public SellPrice(String id, Date date, BigDecimal buyPrice, BigDecimal sellingPrice, String remarks, Boolean sale){
		this.id = id;
		this.date = date;
		this.buyPrice = buyPrice;
		this.sellingPrice = sellingPrice;
		this.remarks = remarks;
		this.sale = sale;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Boolean getSale() {
		return sale;
	}

	public void setSale(Boolean sale) {
		this.sale = sale;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
