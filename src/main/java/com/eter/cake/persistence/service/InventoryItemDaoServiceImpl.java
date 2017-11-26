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
import com.eter.cake.persistence.entity.InventoryItem;
import com.eter.cake.persistence.entity.rest.KeyValue;
import com.eter.cake.persistence.repo.InventoryItemRepository;
import com.eter.response.entity.CommonPaging;

public class InventoryItemDaoServiceImpl extends BaseImpl implements InventoryItemDaoService {

	@Autowired
	protected InventoryItemRepository repo;
	
	@Override
	public CommonPaging<InventoryItem> getPaging(int pageSize, int page, String sortDir, String sort, List<KeyValue> filter) {
		CommonPaging<InventoryItem> paging = new CommonPaging<InventoryItem>();
		paging.setPage(page);
		paging.setRowPerPage(pageSize);
		paging.setTotalData(countListPaging(filter));
		paging.setData(getListPaging(paging.getStartRow(), pageSize, sortDir, sort, filter));
		return paging;
	}
	
	@Override
	public List<InventoryItem> getListPaging(int startRow, int rowPerPage, String sortDir, String sort, List<KeyValue> filter) {
		CriteriaBuilder critB = em.getCriteriaBuilder();

		CriteriaQuery<InventoryItem> query = critB.createQuery(InventoryItem.class);
		Root<InventoryItem> root = query.from(InventoryItem.class);
		Order o = sortDir.equals(Constants.ORDER_ASC) ? critB.asc(root.get(sort)) : critB.desc(root.get(sort));

		List<Predicate> predicates = createSingleSearchPredicate(root, filter);
		
		query.select(root).where(
				critB.and(predicates.toArray(new Predicate[predicates.size()]))
        ).orderBy(o);
		
		TypedQuery<InventoryItem> q = em.createQuery(query);
		q.setFirstResult(startRow);
		q.setMaxResults(rowPerPage);

		return q.getResultList();
		
	}

	@Override
	public int countListPaging(List<KeyValue> filter) {
		CriteriaBuilder critB = em.getCriteriaBuilder();

		CriteriaQuery<Long> query = critB.createQuery(Long.class);
		Root<InventoryItem> root = query.from(InventoryItem.class);
		
		List<Predicate> predicates = createSingleSearchPredicate(root, filter);
		
		query.select(critB.count(root)).where(
				critB.and(predicates.toArray(new Predicate[predicates.size()]))
        );

		return em.createQuery(query).getSingleResult().intValue();
	}

	@Override
	public InventoryItem getById(String id) {
		return repo.findByIdAndActiveFlag(id, Constants.ActiveFlag.ACTIVE);
	}

	@Override
	public InventoryItem save(InventoryItem inventoryItem) {
		return repo.save(inventoryItem);
	}
}
