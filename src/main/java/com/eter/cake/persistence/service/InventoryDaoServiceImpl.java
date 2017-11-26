package com.eter.cake.persistence.service;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;

import com.eter.cake.Constants;
import com.eter.cake.persistence.entity.Inventory;
import com.eter.cake.persistence.entity.InventoryItem;
import com.eter.cake.persistence.entity.rest.KeyValue;
import com.eter.cake.persistence.repo.InventoryRepository;
import com.eter.response.entity.CommonPaging;

public class InventoryDaoServiceImpl extends BaseImpl implements InventoryDaoService {

	@Autowired
	protected InventoryRepository repo;
	
	@Override
	public CommonPaging<Inventory> getPaging(int pageSize, int page, String sortDir, String sort, List<KeyValue> filter) {
		CommonPaging<Inventory> paging = new CommonPaging<Inventory>();
		paging.setPage(page);
		paging.setRowPerPage(pageSize);
		paging.setTotalData(countListPaging(filter));
		paging.setData(getListPaging(paging.getStartRow(), pageSize, sortDir, sort, filter));
		return paging;
	}
	
	@Override
	public List<Inventory> getListPaging(int startRow, int rowPerPage, String sortDir, String sort, List<KeyValue> filter) {
		CriteriaBuilder critB = em.getCriteriaBuilder();

		CriteriaQuery<Inventory> query = critB.createQuery(Inventory.class);
		Root<Inventory> root = query.from(Inventory.class);
		Order o = sortDir.equals(Constants.ORDER_ASC) ? critB.asc(root.get(sort)) : critB.desc(root.get(sort));

		List<Predicate> predicates = createSingleSearchPredicate(root, filter);
		
		query.select(root).where(
				critB.and(predicates.toArray(new Predicate[predicates.size()]))
        ).orderBy(o);
		
		TypedQuery<Inventory> q = em.createQuery(query);
		q.setFirstResult(startRow);
		q.setMaxResults(rowPerPage);

		return q.getResultList();
		
	}

	@Override
	public int countListPaging(List<KeyValue> filter) {
		CriteriaBuilder critB = em.getCriteriaBuilder();

		CriteriaQuery<Long> query = critB.createQuery(Long.class);
		Root<Inventory> root = query.from(Inventory.class);
		
		List<Predicate> predicates = createSingleSearchPredicate(root, filter);
		
		query.select(critB.count(root)).where(
				critB.and(predicates.toArray(new Predicate[predicates.size()]))
        );

		return em.createQuery(query).getSingleResult().intValue();
	}

	@Override
	public Inventory getById(String id) {
		return repo.findByIdAndActiveFlag(id, Constants.ActiveFlag.ACTIVE);
	}

	@Override
	public Inventory save(Inventory inventory) throws Exception{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		Inventory saved = null;
		
		try{
			tx.begin();
			
			saved = em.merge(inventory);
			
			if(inventory.getItems()!=null && !inventory.getItems().isEmpty()){
				for (Iterator<InventoryItem> it = inventory.getItems().iterator(); it.hasNext();) {
					InventoryItem item = it.next();
					item.setInventory(saved);
	
		            em.persist(item);
		            em.flush();
		            em.clear();
		        }
			}
			tx.commit();
			
		}catch(Exception e){
			if(tx!=null && tx.isActive()){
				tx.rollback();
			}
			saved = null;
			throw e;
		}
		
		return saved;
	}
}
