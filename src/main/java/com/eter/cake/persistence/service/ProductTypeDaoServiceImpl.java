package com.eter.cake.persistence.service;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;

import com.eter.cake.Constants;
import com.eter.cake.persistence.entity.ProductType;
import com.eter.cake.persistence.entity.rest.KeyValue;
import com.eter.cake.persistence.repo.ProductTypeRepository;
import com.eter.response.entity.CommonPaging;

public class ProductTypeDaoServiceImpl extends BaseImpl implements ProductTypeDaoService {

	@Autowired
	ProductTypeRepository repo;
	
	@Override
	public CommonPaging<ProductType> getPaging(int pageSize, int page, String sortDir, String sort, List<KeyValue> filter) {
		CommonPaging<ProductType> paging = new CommonPaging<ProductType>();
		paging.setPage(page);
		paging.setRowPerPage(pageSize);
		paging.setTotalData(countListPaging(filter));
		paging.setData(getListPaging(paging.getStartRow(), pageSize, sortDir, sort, filter));
		return paging;
	}
	
	@Override
	public List<ProductType> getListPaging(int startRow, int rowPerPage, String sortDir, String sort, List<KeyValue> filter) {
		CriteriaBuilder critB = em.getCriteriaBuilder();

		CriteriaQuery<ProductType> query = critB.createQuery(ProductType.class);
		Root<ProductType> root = query.from(ProductType.class);
		Order o = sortDir.equals(Constants.ORDER_ASC) ? critB.asc(root.get(sort)) : critB.desc(root.get(sort));

		List<Predicate> predicates = createSingleSearchPredicate(root, filter);
		
		query.select(root).where(
				critB.and(predicates.toArray(new Predicate[predicates.size()]))
        ).orderBy(o);
		
		TypedQuery<ProductType> q = em.createQuery(query);
		q.setFirstResult(startRow);
		q.setMaxResults(rowPerPage);

		return q.getResultList();
		
	}

	@Override
	public int countListPaging(List<KeyValue> filter) {
		CriteriaBuilder critB = em.getCriteriaBuilder();

		CriteriaQuery<Long> query = critB.createQuery(Long.class);
		Root<ProductType> root = query.from(ProductType.class);
		
		List<Predicate> predicates = createSingleSearchPredicate(root, filter);
		
		query.select(critB.count(root)).where(
				critB.and(predicates.toArray(new Predicate[predicates.size()]))
        );

		return em.createQuery(query).getSingleResult().intValue();
	}

	/*private List<Predicate> createSingleSearchPredicate(Root<ProductType> root, List<KeyValue> filter) {	
		if(filter.isEmpty()){
        	return new ArrayList<>();
        }
		
        List<Predicate> predicates = new ArrayList<>();
		CriteriaBuilder critB = em.getCriteriaBuilder();
		
		for (int i = 0; i < filter.size(); i++) {
			KeyValue keyValue = filter.get(i);
			
			if (!StringUtils.isEmpty(keyValue.getKey())) {
	        	if(!StringUtils.isEmpty(keyValue.getValue())){
	        		if (isNumeric(keyValue.getValue())) {
		                predicates.add(critB.equal(root.get(keyValue.getKey()), keyValue.getValue()));
		            } else {
		            	predicates.add(critB.like(root.get(keyValue.getKey()), "%" + keyValue.getValue() + "%"));
		            }
	        	}	        	
	        }
		}
		
		return predicates;
    }*/
	
	@Override
	public ProductType getById(String id) {
		return repo.findByIdAndActiveFlag(id, Constants.ActiveFlag.ACTIVE);
	}

	@Override
	public ProductType save(ProductType productType) {
		return repo.save(productType);
	}
}
