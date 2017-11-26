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
import com.eter.cake.persistence.entity.Product;
import com.eter.cake.persistence.entity.SellPrice;
import com.eter.cake.persistence.entity.rest.KeyValue;
import com.eter.cake.persistence.repo.SellPriceRepository;
import com.eter.response.entity.CommonPaging;

@SuppressWarnings("unchecked")
public class SellPriceDaoServiceImpl extends BaseImpl implements SellPriceDaoService {

	@Autowired
	protected SellPriceRepository repo;
	
	@Override
	public SellPrice getById(String id) {
		return repo.findByIdAndActiveFlag(id, Constants.ActiveFlag.ACTIVE);
	}

	@Override
	public <T> T save(T inventoryItem) {
		return (T) repo.save((SellPrice) inventoryItem);
	}
	
	@Override
	public <T> CommonPaging<T> getPaging(int pageSize, int page, String sortDir, String sort, List<KeyValue> filter) {
		CommonPaging<SellPrice> paging = new CommonPaging<SellPrice>();
		paging.setPage(page);
		paging.setRowPerPage(pageSize);
		paging.setTotalData(countListPaging(filter));
		paging.setData(getListPaging(paging.getStartRow(), pageSize, sortDir, sort, filter));
		return (CommonPaging<T>) paging;
	}
	
	@Override
	public <T> List<T> getListPaging(int startRow, int rowPerPage, String sortDir, String sort, List<KeyValue> filter) {
		CriteriaBuilder critB = em.getCriteriaBuilder();

		CriteriaQuery<SellPrice> query = critB.createQuery(SellPrice.class);
		Root<SellPrice> root = query.from(SellPrice.class);
		Order o = sortDir.equals(Constants.ORDER_ASC) ? critB.asc(root.get(sort)) : critB.desc(root.get(sort));

		List<Predicate> predicates = createSingleSearchPredicate(root, filter);
		
		query.select(root).where(
				critB.and(predicates.toArray(new Predicate[predicates.size()]))
        ).orderBy(o);
		
		TypedQuery<SellPrice> q = em.createQuery(query);
		q.setFirstResult(startRow);
		q.setMaxResults(rowPerPage);

		return (List<T>) q.getResultList();
		
	}

	@Override
	public int countListPaging(List<KeyValue> filter) {
		CriteriaBuilder critB = em.getCriteriaBuilder();

		CriteriaQuery<Long> query = critB.createQuery(Long.class);
		Root<SellPrice> root = query.from(SellPrice.class);
		
		List<Predicate> predicates = createSingleSearchPredicate(root, filter);
		
		query.select(critB.count(root)).where(
				critB.and(predicates.toArray(new Predicate[predicates.size()]))
        );

		return em.createQuery(query).getSingleResult().intValue();
	}

	@Override
	public SellPrice getLatestPriceByProduct(Product product) {
		return repo.findFirstByActiveFlagAndProductOrderByDateDesc(Constants.ActiveFlag.ACTIVE, product);
	}

	@Override
	public List<SellPrice> getSellPricesByProducts(List<Product> products) {
		/*TypedQuery<SellPrice> q = em.createQuery(SELECT_LATEST_SELL_PRICE_PER_PRODUCT, SellPrice.class);
		q.setParameter("products", products);
		
		List<SellPrice> sellPrices = q.getResultList();
		
		return sellPrices;*/
		
		return null;
		
		
		/*CriteriaBuilder critB = em.getCriteriaBuilder();

		CriteriaQuery<SellPrice> query = critB.createQuery(SellPrice.class);
		Root<SellPrice> root = query.from(SellPrice.class);
		Order o = critB.asc(root.get("date"));
	
		List<Predicate> predicates = createSingleSearchPredicate(root, new ArrayList<>());
		
		Expression<Product> exp = root.get("product");
		predicates.add(exp.in(products));
		
		Subquery<Date> sq = query.subquery(Date.class);
		Root<SellPrice> root2 = sq.from(SellPrice.class);		
		sq.select(critB.greatest(root2.<Date>get("date")));
		
		predicates.add(critB.equal(root.get("date"), sq));
		
		query.groupBy(root.get("product"));
		query.select(root).where(
				critB.and(predicates.toArray(new Predicate[predicates.size()]))
        ).orderBy(o);
		
		TypedQuery<SellPrice> q = em.createQuery(query);

		return (List<SellPrice>) q.getResultList();*/
	}

}
