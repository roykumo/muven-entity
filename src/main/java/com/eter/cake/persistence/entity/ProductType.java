package com.eter.cake.persistence.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFilter;

@Entity(name="ProductTypeEntity")
@Table(name = "product_type")
@JsonFilter(value="jsonFilterProductTypeEntity")
public class ProductType extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -4757209931096634127L;

	public abstract interface Constant{
		public static final String ID_FIELD = "id";
		public static final String CODE_FIELD = "code";
		public static final String DESCRIPTION_FIELD = "description";
		public static final String EXPIRATION_FIELD = "expiration";
		public static final String CREATED_DATE_FIELD = "createdDate";
		public static final String MODIFIED_DATE_FIELD = "modifiedDate";
	}
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	private String code;
	private String description;
	private Boolean expiration;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getExpiration() {
		return expiration;
	}
	public void setExpiration(Boolean expiration) {
		this.expiration = expiration;
	}
}
