package com.eter.cake.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import com.eter.cake.Constants;
import com.eter.cake.persistence.entity.Product;
import com.eter.cake.persistence.entity.ProductType;
import com.eter.cake.persistence.entity.rest.KeyValue;

public abstract class BaseImpl {

	@PersistenceContext
	protected EntityManager em;
	
	@PersistenceUnit
	protected EntityManagerFactory emf;

	protected boolean isNumeric(String value){
		try {
			if (value.matches("^[0-9]+")) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	protected <T> List<Predicate> createSingleSearchPredicate(Root<T> root, List<KeyValue> filter) {	

        List<Predicate> predicates = new ArrayList<>();
		CriteriaBuilder critB = em.getCriteriaBuilder();
		predicates.add(critB.equal(root.get("activeFlag"), Constants.ActiveFlag.ACTIVE));
		
		if(!filter.isEmpty()){
			for (int i = 0; i < filter.size(); i++) {
				KeyValue keyValue = filter.get(i);
				
				if (!StringUtils.isEmpty(keyValue.getKey())) {
		        	if(!StringUtils.isEmpty(keyValue.getValue())){
		        		if(keyValue.getKey().equalsIgnoreCase("productType")){
		        			ProductType type = new ProductType();
			        		type.setId(keyValue.getValue());
			        		predicates.add(critB.equal(root.get("type"), type));
		        		}else if(keyValue.getKey().equalsIgnoreCase("inventory.product")){
		        			Product prod = new Product();
			        		prod.setId(keyValue.getValue());
			        		predicates.add(critB.equal(root.get("product"), prod));
		        		}else if(keyValue.getKey().equalsIgnoreCase("sellPrice.product")){
		        			Product prod = new Product();
			        		prod.setId(keyValue.getValue());
			        		predicates.add(critB.equal(root.get("product"), prod));
		        		}else if (isNumeric(keyValue.getValue())) {
			                predicates.add(critB.equal(root.get(keyValue.getKey()), keyValue.getValue()));
			            } else {
			            	predicates.add(critB.like(root.get(keyValue.getKey()), "%" + keyValue.getValue() + "%"));
			            }
		        	}	        	
		        }
			}
        }
		
		return predicates;
    }
}
