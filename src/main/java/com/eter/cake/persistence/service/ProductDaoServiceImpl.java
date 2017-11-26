package com.eter.cake.persistence.service;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.eter.cake.Constants;
import com.eter.cake.persistence.entity.Product;
import com.eter.cake.persistence.entity.ProductType;
import com.eter.cake.persistence.entity.rest.KeyValue;
import com.eter.cake.persistence.entity.rest.ProductStock;
import com.eter.cake.persistence.repo.ProductRepository;
import com.eter.response.entity.CommonPaging;

public class ProductDaoServiceImpl extends BaseImpl implements ProductDaoService {
    private Logger logger = LoggerFactory.getLogger(ProductDaoServiceImpl.class);

	@Autowired
	protected ProductRepository repo;
	
	@Autowired
	protected SellPriceDaoService sellPriceService;
	
	@Override
	public CommonPaging<Product> getPaging(int pageSize, int page, String sortDir, String sort, List<KeyValue> filter) {
		CommonPaging<Product> paging = new CommonPaging<Product>();
		paging.setPage(page);
		paging.setRowPerPage(pageSize);
		paging.setTotalData(countListPaging(filter));
		paging.setData(getListPaging(paging.getStartRow(), pageSize, sortDir, sort, filter));
		return paging;
	}
	
	@Override
	public List<Product> getListPaging(int startRow, int rowPerPage, String sortDir, String sort, List<KeyValue> filter) {
		CriteriaBuilder critB = em.getCriteriaBuilder();

		CriteriaQuery<Product> query = critB.createQuery(Product.class);
		Root<Product> root = query.from(Product.class);
		Order o = sortDir.equals(Constants.ORDER_ASC) ? critB.asc(root.get(sort)) : critB.desc(root.get(sort));

		List<Predicate> predicates = createSingleSearchPredicate(root, filter);
		
		query.select(root).where(
				critB.and(predicates.toArray(new Predicate[predicates.size()]))
        ).orderBy(o);
		
		TypedQuery<Product> q = em.createQuery(query);
		q.setFirstResult(startRow);
		q.setMaxResults(rowPerPage);

		return q.getResultList();
		
	}

	@Override
	public int countListPaging(List<KeyValue> filter) {
		CriteriaBuilder critB = em.getCriteriaBuilder();

		CriteriaQuery<Long> query = critB.createQuery(Long.class);
		Root<Product> root = query.from(Product.class);
		
		List<Predicate> predicates = createSingleSearchPredicate(root, filter);
		
		query.select(critB.count(root)).where(
				critB.and(predicates.toArray(new Predicate[predicates.size()]))
        );

		return em.createQuery(query).getSingleResult().intValue();
	}

	/*private List<Predicate> createSingleSearchPredicate(Root<Product> root, List<KeyValue> filter) {	
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
	public Product getById(String id) {
		return repo.findByIdAndActiveFlag(id, Constants.ActiveFlag.ACTIVE);
	}

	@Override
	public Product save(Product product) {
		return repo.save(product);
	}

	@Override
	public boolean delete(Product product) {
		try{
			product.setActiveFlag(Constants.ActiveFlag.INACTIVE);
			repo.save(product);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage());
			return false;
		}
	}
	
	private static final String SELECT_PRODUCT_STOCK_PER_TYPE = "select new com.eter.cake.persistence.entity.rest.ProductStock(it.product as product, sum(it.quantity) as quantity, sum(it.purchasePrice * it.quantity) as buyPrice, min(it.expiredDate) as expiredDate) from InventoryItemEntity it where it.product.type.id =:typeId GROUP BY it.product ";
	
	@Override
	public List<ProductStock> getProductStock(ProductType type) {
		TypedQuery<ProductStock> q = em.createQuery(SELECT_PRODUCT_STOCK_PER_TYPE, ProductStock.class);
		q.setParameter("typeId", type.getId());
		
		List<ProductStock> stocks = q.getResultList();
		
		try{
			for (ProductStock stock : stocks) {
				stock.setSellPrice(sellPriceService.getLatestPriceByProduct(stock.getProduct()));
			}
			/*List<SellPrice> sellPrices = sellPriceService.getSellPricesByProducts(stocks.stream().map(ProductStock::getProduct).collect(Collectors.toList()));
			if(sellPrices!=null && !sellPrices.isEmpty()){
				Map<String, SellPrice> mapPrice = new HashMap<>();
				for (SellPrice price : sellPrices) {
					mapPrice.put(price.getProduct().getId(), price);
				}
				
				for (ProductStock stock : stocks) {
					stock.setSellPrice(mapPrice.get(stock.getProduct().getId()));
				}
			}*/
		}catch(Exception e){
			
		}
		
		return stocks;
	}
	
	
}
