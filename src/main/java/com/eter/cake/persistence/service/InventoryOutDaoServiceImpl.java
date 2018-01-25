package com.eter.cake.persistence.service;

import com.eter.cake.Constants;
import com.eter.cake.persistence.entity.Inventory;
import com.eter.cake.persistence.entity.InventoryItem;
import com.eter.cake.persistence.entity.InventoryOut;
import com.eter.cake.persistence.entity.InventoryItemOut;
import com.eter.cake.persistence.entity.rest.KeyValue;
import com.eter.cake.persistence.repo.InventoryOutRepository;
import com.eter.response.entity.CommonPaging;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Iterator;
import java.util.List;

public class InventoryOutDaoServiceImpl extends BaseImpl implements InventoryOutDaoService {

	@Autowired
	protected InventoryOutRepository repo;
	
	@Override
	public CommonPaging<InventoryOut> getPaging(int pageSize, int page, String sortDir, String sort, List<KeyValue> filter) {
		CommonPaging<InventoryOut> paging = new CommonPaging<InventoryOut>();
		paging.setPage(page);
		paging.setRowPerPage(pageSize);
		paging.setTotalData(countListPaging(filter));
		paging.setData(getListPaging(paging.getStartRow(), pageSize, sortDir, sort, filter));
		return paging;
	}
	
	@Override
	public List<InventoryOut> getListPaging(int startRow, int rowPerPage, String sortDir, String sort, List<KeyValue> filter) {
		CriteriaBuilder critB = em.getCriteriaBuilder();

		CriteriaQuery<InventoryOut> query = critB.createQuery(InventoryOut.class);
		Root<InventoryOut> root = query.from(InventoryOut.class);
		Order o = sortDir.equals(Constants.ORDER_ASC) ? critB.asc(root.get(sort)) : critB.desc(root.get(sort));

		List<Predicate> predicates = createSingleSearchPredicate(root, filter);
		
		query.select(root).where(
				critB.and(predicates.toArray(new Predicate[predicates.size()]))
        ).orderBy(o);
		
		TypedQuery<InventoryOut> q = em.createQuery(query);
		q.setFirstResult(startRow);
		q.setMaxResults(rowPerPage);

		return q.getResultList();
		
	}

	@Override
	public int countListPaging(List<KeyValue> filter) {
		CriteriaBuilder critB = em.getCriteriaBuilder();

		CriteriaQuery<Long> query = critB.createQuery(Long.class);
		Root<InventoryOut> root = query.from(InventoryOut.class);
		
		List<Predicate> predicates = createSingleSearchPredicate(root, filter);
		
		query.select(critB.count(root)).where(
				critB.and(predicates.toArray(new Predicate[predicates.size()]))
        );

		return em.createQuery(query).getSingleResult().intValue();
	}

	@Override
	public InventoryOut getById(String id) {
		return repo.findByIdAndActiveFlag(id, Constants.ActiveFlag.ACTIVE);
	}

	@Override
	public InventoryOut save(InventoryOut inventoryOut) throws Exception{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		InventoryOut saved = null;
		
		try{
			tx.begin();

			//inventory in
			if(inventoryOut.getInventoryIn()!=null){
				Inventory savedInventory = em.merge(inventoryOut.getInventoryIn());
				if(inventoryOut.getInventoryIn().getItems()!=null && !inventoryOut.getInventoryIn().getItems().isEmpty()){
					for (Iterator<InventoryItem> it = inventoryOut.getInventoryIn().getItems().iterator(); it.hasNext();) {
						InventoryItem item = it.next();
						item.setInventory(savedInventory);

						em.merge(item);
						em.flush();
						em.clear();
					}
				}
				inventoryOut.setInventoryIn(em.merge(savedInventory));
			}

			//inventory out
			saved = em.merge(inventoryOut);
			if(inventoryOut.getItems()!=null && !inventoryOut.getItems().isEmpty()){
				for (Iterator<InventoryItemOut> it = inventoryOut.getItems().iterator(); it.hasNext();) {
					InventoryItemOut item = it.next();
					item.setInventoryOut(saved);
	
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
