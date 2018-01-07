package com.eter.cake.persistence.service;

import com.eter.cake.Constants;
import com.eter.cake.persistence.entity.ProductCategory;
import com.eter.cake.persistence.entity.rest.KeyValue;
import com.eter.cake.persistence.repo.ProductCategoryRepository;
import com.eter.response.entity.CommonPaging;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class ProductCategoryDaoServiceImpl extends BaseImpl implements ProductCategoryDaoService {

	@Autowired
	ProductCategoryRepository repo;
	
	@Override
	public CommonPaging<ProductCategory> getPaging(int pageSize, int page, String sortDir, String sort, List<KeyValue> filter) {
		CommonPaging<ProductCategory> paging = new CommonPaging<ProductCategory>();
		paging.setPage(page);
		paging.setRowPerPage(pageSize);
		paging.setTotalData(countListPaging(filter));
		paging.setData(getListPaging(paging.getStartRow(), pageSize, sortDir, sort, filter));
		return paging;
	}
	
	@Override
	public List<ProductCategory> getListPaging(int startRow, int rowPerPage, String sortDir, String sort, List<KeyValue> filter) {
		CriteriaBuilder critB = em.getCriteriaBuilder();

		CriteriaQuery<ProductCategory> query = critB.createQuery(ProductCategory.class);
		Root<ProductCategory> root = query.from(ProductCategory.class);
		Order o = sortDir.equals(Constants.ORDER_ASC) ? critB.asc(root.get(sort)) : critB.desc(root.get(sort));

		List<Predicate> predicates = createSingleSearchPredicate(root, filter);
		
		query.select(root).where(
				critB.and(predicates.toArray(new Predicate[predicates.size()]))
        ).orderBy(o);
		
		TypedQuery<ProductCategory> q = em.createQuery(query);
		q.setFirstResult(startRow);
		q.setMaxResults(rowPerPage);

		return q.getResultList();
		
	}

	@Override
	public int countListPaging(List<KeyValue> filter) {
		CriteriaBuilder critB = em.getCriteriaBuilder();

		CriteriaQuery<Long> query = critB.createQuery(Long.class);
		Root<ProductCategory> root = query.from(ProductCategory.class);
		
		List<Predicate> predicates = createSingleSearchPredicate(root, filter);
		
		query.select(critB.count(root)).where(
				critB.and(predicates.toArray(new Predicate[predicates.size()]))
        );

		return em.createQuery(query).getSingleResult().intValue();
	}

	@Override
	public ProductCategory getById(String id) {
		return repo.findByIdAndActiveFlag(id, Constants.ActiveFlag.ACTIVE);
	}

	@Override
	public ProductCategory save(ProductCategory productCategory) {
		return repo.save(productCategory);
	}
}
