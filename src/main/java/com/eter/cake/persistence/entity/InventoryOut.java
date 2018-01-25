package com.eter.cake.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Cascade;
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
		public static final String TOTAL_PRICE_FIELD = "totalPrice";
		public static final String DATE_FIELD = "date";
        public static final String TYPE_FIELD = "type";
        public static final String REMARKS_FIELD = "remarks";
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
	private String remarks;
    private String transactionCode;

	@ManyToOne()
	@JoinColumn(name = "inventory_in")
	private Inventory inventoryIn;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "payment")
    private Payment payment;

	@ManyToOne()
	@JoinColumn(name = "product_type")
	private ProductType productType;

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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
}
