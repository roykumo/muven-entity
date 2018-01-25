package com.eter.cake.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name="PaymentEntity")
@Table(name = "payment")
@JsonFilter(value="jsonFilterPaymentEntity")
public class Payment extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -9051169886520781044L;

	public abstract interface Constant{
		public static final String ID_FIELD = "id";
		public static final String TYPE_FIELD = "product";
		public static final String CARD_REFF_NO_FIELD = "cardReffNo";
		public static final String CARD_TYPE_FIELD = "cardType";
		public static final String PAY_AMOUNT_FIELD = "payAmount";
		public static final String TOTAL_AMOUNT_FIELD = "totalAmount";
		public static final String REMARKS_FIELD = "remarks";
		public static final String SALE_FIELD = "sale";
		public static final String CREATED_DATE_FIELD = "createdDate";
		public static final String MODIFIED_DATE_FIELD = "modifiedDate";
	}

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	private String type;
	private String receiptNo;
	private String cardReffNo;
	private String cardType;
	private BigDecimal payAmount;
	private BigDecimal totalAmount;
	private String remarks;

	public Payment(){

	}

	public Payment(String id, String type, String receiptNo, String cardReffNo, String cardType, BigDecimal payAmount, BigDecimal totalAmount, String remarks) {
		this.id = id;
		this.type = type;
		this.receiptNo = receiptNo;
		this.cardReffNo = cardReffNo;
		this.cardType = cardType;
		this.payAmount = payAmount;
		this.totalAmount = totalAmount;
		this.remarks = remarks;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getCardReffNo() {
		return cardReffNo;
	}

	public void setCardReffNo(String cardReffNo) {
		this.cardReffNo = cardReffNo;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
