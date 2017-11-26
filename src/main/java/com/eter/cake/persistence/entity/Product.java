package com.eter.cake.persistence.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFilter;

@Entity(name="ProductEntity")
@Table(name = "product")
@JsonFilter(value="jsonFilterProductEntity")
public class Product extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 2548345267648777885L;
	
	public abstract interface Constant{
		public static final String ID_FIELD = "id";
		public static final String BARCODE_FIELD = "barcode";
		public static final String NAME_FIELD = "name";
		public static final String TYPE_FIELD = "type";
		public static final String ALERT_RED_FIELD = "expiraalertRedtion";
		public static final String ALERT_YELLOW_FIELD = "alertYellow";
		public static final String ALERT_GREEN_FIELD = "alertGreen";
		public static final String CREATED_DATE_FIELD = "createdDate";
		public static final String MODIFIED_DATE_FIELD = "modifiedDate";
	}
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	private String barcode;
	private String name;

    @ManyToOne()
    @JoinColumn(name = "type")    
	private ProductType type;
    
	private Integer alertRed;
	private Integer alertYellow;
	private Integer alertGreen;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ProductType getType() {
		return type;
	}
	public void setType(ProductType type) {
		this.type = type;
	}
	public Integer getAlertRed() {
		return alertRed;
	}
	public void setAlertRed(Integer alertRed) {
		this.alertRed = alertRed;
	}
	public Integer getAlertYellow() {
		return alertYellow;
	}
	public void setAlertYellow(Integer alertYellow) {
		this.alertYellow = alertYellow;
	}
	public Integer getAlertGreen() {
		return alertGreen;
	}
	public void setAlertGreen(Integer alertGreen) {
		this.alertGreen = alertGreen;
	}
}
