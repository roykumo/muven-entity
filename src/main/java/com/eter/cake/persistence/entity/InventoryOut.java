package com.eter.cake.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity(name="InventoryOutEntity")
@Table(name = "inventory_out")
@JsonFilter(value="jsonFilterInventoryOutEntity")
public class InventoryOut extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1;

	public abstract interface Constant{
		public static final String ID_FIELD = "id";
		public static final String TRANSACTION_CODE_FIELD = "transactionCode";
		public static final String DATE_FIELD = "date";
		public static final String CREATED_DATE_FIELD = "createdDate";
		public static final String MODIFIED_DATE_FIELD = "modifiedDate";
		public static final String OUT_TYPE_REPACKING = "RP";
		public static final String OUT_TYPE_STOCK_OPNAME = "SO";
	}
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone="GMT+7")
	private Date date;
	private BigDecimal totalPrice;
	private String type;

	@ManyToOne()
	@JoinColumn(name = "inventory_in")
	private Inventory inventoryIn;

	@OneToMany(mappedBy="inventoryOut")
    private List<InventoryItemOut> items;

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

	public List<InventoryItemOut> getItems() {
		return items;
	}

	public void setItems(List<InventoryItemOut> items) {
		this.items = items;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Inventory getInventoryIn() {
		return inventoryIn;
	}

	public void setInventoryIn(Inventory inventoryIn) {
		this.inventoryIn = inventoryIn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
