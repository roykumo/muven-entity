package com.eter.cake.persistence.service;

import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.eter.cake.persistence.entity.ProductType;
import com.eter.cake.persistence.entity.SellPrice;
import com.eter.cake.persistence.entity.rest.KeyValue;
import com.eter.cake.persistence.entity.rest.SaleNotification;
import com.eter.cake.persistence.repo.SellPriceRepository;
import com.eter.response.entity.CommonPaging;

public class NotificationDaoServiceImpl extends BaseImpl implements NotificationDaoService {

    private Logger logger = LoggerFactory.getLogger(NotificationDaoServiceImpl.class);
    
	@Autowired
	protected SellPriceRepository repo;

	@Autowired
	protected SellPriceDaoService sellPriceService;
	
	private static final String SELECT_SALE_NOTIFICATION_PER_TYPE = "select new com.eter.cake.persistence.entity.rest.SaleNotification(it.product as product, sum(it.quantity) as quantity, sum(it.purchasePrice * it.quantity) as buyPrice, min(it.expiredDate) as expiredDate) from InventoryItemEntity it where it.product.type.id =:typeId GROUP BY it.product ORDER BY expiredDate ASC ";
	
	@Override
	public List<SaleNotification> getSaleNotifications(ProductType type) {
		TypedQuery<SaleNotification> q = em.createQuery(SELECT_SALE_NOTIFICATION_PER_TYPE, SaleNotification.class);
		q.setParameter("typeId", type.getId());
		
		List<SaleNotification> saleNotifications = q.getResultList();

		if(saleNotifications!=null && !saleNotifications.isEmpty()){
			try {
				for (int i = saleNotifications.size()-1; i >= 0; i--) {
					SaleNotification notif = saleNotifications.get(i);
					SellPrice sellPrice = sellPriceService.getLatestPriceByProduct(notif.getProduct());
					if(sellPrice!=null && sellPrice.getSale()){
						notif.setSellPrice(sellPrice);
					}else{
						saleNotifications.remove(i);
					}
				}
			/*for (SaleNotification notif : saleNotifications) {
				SellPrice sellPrice = sellPriceService.getLatestPriceByProduct(notif.getProduct());
				if(sellPrice!=null && sellPrice.getSale()){
					notif.setSellPrice(sellPrice);
				}else{
					saleNotifications.remove(notif);
				}
			}*/
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		
		return saleNotifications;
	}

	@Override
	public <T> T getById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> getListPaging(int startRow, int rowPerPage,
			String sortDir, String sort, List<KeyValue> filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countListPaging(List<KeyValue> filter) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T> T save(T model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> CommonPaging<T> getPaging(int pageSize, int page,
			String sortDir, String sort, List<KeyValue> filter) {
		// TODO Auto-generated method stub
		return null;
	}
}
