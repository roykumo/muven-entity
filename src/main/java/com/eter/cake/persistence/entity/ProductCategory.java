package com.eter.cake.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="ProductCategoryEntity")
@Table(name = "product_category")
@JsonFilter(value="jsonFilterProductCategoryEntity")
public class ProductCategory extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -4757209931096634127L;

	public abstract interface Constant{
		public static final String ID_FIELD = "id";
		public static final String CODE_FIELD = "code";
		public static final String DESCRIPTION_FIELD = "description";
		public static final String PARENT_FIELD = "parent";
		public static final String CREATED_DATE_FIELD = "createdDate";
		public static final String MODIFIED_DATE_FIELD = "modifiedDate";
	}
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	private String code;
	private String description;

	@ManyToOne
	@JoinColumn(name="parent")
	private ProductCategory parent;

	@ManyToOne
	@JoinColumn(name="type")
	private ProductType type;
	
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
	public ProductCategory getParent() {
		return parent;
	}
	public void setParent(ProductCategory parent) {
		this.parent = parent;
	}
	public ProductType getType() {
		return type;
	}
	public void setType(ProductType type) {
		this.type = type;
	}

}
