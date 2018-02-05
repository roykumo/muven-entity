package com.eter.cake.persistence.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.eter.cake.persistence.entity.InventoryItemOut;
import com.eter.cake.persistence.entity.rest.ProductStock;
import com.eter.cake.persistence.entity.rest.StatusNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.eter.cake.persistence.entity.ProductType;
import com.eter.cake.persistence.entity.SellPrice;
import com.eter.cake.persistence.entity.rest.KeyValue;
import com.eter.cake.persistence.entity.rest.SaleNotification;
import com.eter.cake.persistence.repo.SellPriceRepository;
import com.eter.response.entity.CommonPaging;
import org.springframework.util.StringUtils;

public class NotificationDaoServiceImpl extends BaseImpl implements NotificationDaoService {

    private Logger logger = LoggerFactory.getLogger(NotificationDaoServiceImpl.class);
    
	@Autowired
	protected SellPriceRepository repo;

	@Autowired
	protected SellPriceDaoService sellPriceService;
	
	private static final String SELECT_SALE_NOTIFICATION_PER_TYPE = "select new com.eter.cake.persistence.entity.rest.SaleNotification(it.product as product, sum(it.quantity) as quantity, sum(it.purchasePrice * it.quantity) as buyPrice, min(it.expiredDate) as expiredDate) from InventoryItemEntity it where it.product.category.type.id =:typeId AND it.product.activeFlag=1 AND it.product.category.type.activeFlag=1 GROUP BY it.product ORDER BY expiredDate ASC ";
	
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

    //private static final String SELECT_PRODUCT_STOCK_PER_TYPE = "select new com.eter.cake.persistence.entity.rest.ProductStock(it.product as product, sum(it.quantity) as quantity, sum(it.purchasePrice * it.quantity) as buyPrice, min(it.expiredDate) as expiredDate) from InventoryItemEntity it where it.product.type.id =:typeId   AND it.product.activeFlag = 1     GROUP BY it.product ";

	//private static final String SELECT_STATUS_AND_STOCK_PER_PRODUCT = "select new com.eter.cake.persistence.entity.rest.StatusNotification(p.id as 'product.id', p.name as 'product.name', p.code as 'product.code', p.barcode as 'product.barcode', sp.id as 'sellPrice.id', sp.date as 'sellPrice.date', sp.buy_price as 'sellPrice.buy_price', sp.selling_price as 'sellPrice.selling_price', sp.sale as 'sellPrice.sale', sum(case when datediff(now(), it.expired_date) <= p.alert_red then it.quantity else 0 end) as quantity_red,  sum(case when datediff(now(), it.expired_date) > p.alert_red and datediff(now(), it.expired_date) <= p.alert_yellow then it.quantity else 0 end) as quantity_yellow,  sum(case when datediff(now(), it.expired_date) > p.alert_yellow and datediff(now(), it.expired_date) <= p.alert_green then it.quantity else 0 end) as quantity_green,  sum(case when datediff(now(), it.expired_date) > p.alert_green and datediff(now(), it.expired_date) <= p.alert_blue then it.quantity else 0 end) as quantity_blue,  coalesce(ito.quantity_out, 0) as quantity_out, sum(it.purchase_price * it.quantity) as buy_price,  min(it.expired_date) as expired_date) from inventory_item it inner join inventory i on it.inventory = i.id  inner join product p on it.product = p.id inner join product_type pt on p.type = pt.id left join sell_price sp on sp.product = p.id left join ( select po.id as po_id, sum(itout.quantity) as quantity_out from inventory_out iout  left join inventory_item_out itout on itout.inventory_out = iout.id inner join product po on itout.product = po.id group by po.id ) ito on p.id = ito.po_id where p.active_flag = 1 and i.type=:type and p.type =:productType group by p.id;";
	private static final String SELECT_STATUS_AND_STOCK_PER_PRODUCT = "select  new com.eter.cake.persistence.entity.rest.StatusNotification( p.id as id,  p as product,  sum( case when ABS(datediff(now(), it.expiredDate)) <= p.alertRed then it.quantity else 0 end ) as quantity_red,  sum( case when ABS(datediff(now(), it.expiredDate)) > p.alertRed  and ABS(datediff(now(), it.expiredDate)) <= p.alertYellow then it.quantity else 0 end ) as quantity_yellow,  sum( case when ABS(datediff(now(), it.expiredDate)) > p.alertYellow  and ABS(datediff(now(), it.expiredDate)) <= p.alertGreen then it.quantity else 0 end ) as quantity_green,  sum( case when ABS(datediff(now(), it.expiredDate)) > p.alertGreen  and ABS(datediff(now(), it.expiredDate)) <= p.alertBlue then it.quantity else 0 end ) as quantity_blue,  coalesce( ( select sum(itout.quantity) as quantity_out from InventoryItemOutEntity itout where itout.product.id = p.id group by itout.product.id\t\t\t ), 0) as quantity_out,  sum(it.purchasePrice * it.quantity) as buy_price , min(it.expiredDate) as expired_date)  from  InventoryItemEntity it  inner join it.inventory i inner join it.product p inner join p.category.type pt  where  p.activeFlag = 1 AND (p.code like :barcode OR p.barcode like :barcode )  and p.category.type.id =:productType  group by p.id";
	private static final String SELECT_STATUS_AND_STOCK_PER_PRODUCT_AND_CATEGORY = "select  new com.eter.cake.persistence.entity.rest.StatusNotification( p.id as id,  p as product,  sum( case when ABS(datediff(now(), it.expiredDate)) <= p.alertRed then it.quantity else 0 end ) as quantity_red,  sum( case when ABS(datediff(now(), it.expiredDate)) > p.alertRed  and ABS(datediff(now(), it.expiredDate)) <= p.alertYellow then it.quantity else 0 end ) as quantity_yellow,  sum( case when ABS(datediff(now(), it.expiredDate)) > p.alertYellow  and ABS(datediff(now(), it.expiredDate)) <= p.alertGreen then it.quantity else 0 end ) as quantity_green,  sum( case when ABS(datediff(now(), it.expiredDate)) > p.alertGreen  and ABS(datediff(now(), it.expiredDate)) <= p.alertBlue then it.quantity else 0 end ) as quantity_blue,  coalesce( ( select sum(itout.quantity) as quantity_out from InventoryItemOutEntity itout where itout.product.id = p.id group by itout.product.id\t\t\t ), 0) as quantity_out,  sum(it.purchasePrice * it.quantity) as buy_price , min(it.expiredDate) as expired_date)  from  InventoryItemEntity it  inner join it.inventory i inner join it.product p inner join p.category.type pt  where  p.activeFlag = 1 AND (p.code like :barcode OR p.barcode like :barcode )  and p.category.type.id =:productType AND p.category.id =:category group by p.id";
	private static final String SELECT_STATUS_AND_STOCK_PER_PRODUCT_AND_GROUP = "select  new com.eter.cake.persistence.entity.rest.StatusNotification( p.id as id,  p as product,  sum( case when ABS(datediff(now(), it.expiredDate)) <= p.alertRed then it.quantity else 0 end ) as quantity_red,  sum( case when ABS(datediff(now(), it.expiredDate)) > p.alertRed  and ABS(datediff(now(), it.expiredDate)) <= p.alertYellow then it.quantity else 0 end ) as quantity_yellow,  sum( case when ABS(datediff(now(), it.expiredDate)) > p.alertYellow  and ABS(datediff(now(), it.expiredDate)) <= p.alertGreen then it.quantity else 0 end ) as quantity_green,  sum( case when ABS(datediff(now(), it.expiredDate)) > p.alertGreen  and ABS(datediff(now(), it.expiredDate)) <= p.alertBlue then it.quantity else 0 end ) as quantity_blue,  coalesce( ( select sum(itout.quantity) as quantity_out from InventoryItemOutEntity itout where itout.product.id = p.id group by itout.product.id\t\t\t ), 0) as quantity_out,  sum(it.purchasePrice * it.quantity) as buy_price , min(it.expiredDate) as expired_date)  from  InventoryItemEntity it  inner join it.inventory i inner join it.product p inner join p.category.type pt  where  p.activeFlag = 1 AND (p.code like :barcode OR p.barcode like :barcode )  and p.category.type.id =:productType AND p.productGroup =:group group by p.id";
	private static final String SELECT_STATUS_AND_STOCK_PER_PRODUCT_AND_CATEGORY_AND_GROUP = "select  new com.eter.cake.persistence.entity.rest.StatusNotification( p.id as id,  p as product,  sum( case when ABS(datediff(now(), it.expiredDate)) <= p.alertRed then it.quantity else 0 end ) as quantity_red,  sum( case when ABS(datediff(now(), it.expiredDate)) > p.alertRed  and ABS(datediff(now(), it.expiredDate)) <= p.alertYellow then it.quantity else 0 end ) as quantity_yellow,  sum( case when ABS(datediff(now(), it.expiredDate)) > p.alertYellow  and ABS(datediff(now(), it.expiredDate)) <= p.alertGreen then it.quantity else 0 end ) as quantity_green,  sum( case when ABS(datediff(now(), it.expiredDate)) > p.alertGreen  and ABS(datediff(now(), it.expiredDate)) <= p.alertBlue then it.quantity else 0 end ) as quantity_blue,  coalesce( ( select sum(itout.quantity) as quantity_out from InventoryItemOutEntity itout where itout.product.id = p.id group by itout.product.id\t\t\t ), 0) as quantity_out,  sum(it.purchasePrice * it.quantity) as buy_price , min(it.expiredDate) as expired_date)  from  InventoryItemEntity it  inner join it.inventory i inner join it.product p inner join p.category.type pt  where  p.activeFlag = 1 AND (p.code like :barcode OR p.barcode like :barcode )  and p.category.type.id =:productType AND p.category.id =:category AND p.productGroup =:group group by p.id";
	//private static final String SELECT_STATUS_AND_STOCK_PER_PRODUCT_FILTER_BY_PU = "select  new com.eter.cake.persistence.entity.rest.StatusNotification( p.id as id,  p as product,  sum( case when datediff(now(), it.expiredDate) <= p.alertRed then it.quantity else 0 end ) as quantity_red,  sum( case when datediff(now(), it.expiredDate) > p.alertRed  and datediff(now(), it.expiredDate) <= p.alertYellow then it.quantity else 0 end ) as quantity_yellow,  sum( case when datediff(now(), it.expiredDate) > p.alertYellow  and datediff(now(), it.expiredDate) <= p.alertGreen then it.quantity else 0 end ) as quantity_green,  sum( case when datediff(now(), it.expiredDate) > p.alertGreen  and datediff(now(), it.expiredDate) <= p.alertBlue then it.quantity else 0 end ) as quantity_blue,  coalesce( ( select sum(itout.quantity) as quantity_out from InventoryItemOutEntity itout where itout.product.id = p.id group by itout.product.id\t\t\t ), 0) as quantity_out,  sum(it.purchasePrice * it.quantity) as buy_price , min(it.expiredDate) as expired_date)  from  InventoryItemEntity it  inner join it.inventory i inner join it.product p inner join p.category.type pt  where  p.activeFlag = 1 AND (p.code like :barcode OR p.barcode like :barcode ) and i.type =:type  and p.category.type.id =:productType  group by p.id";
    @Override
    public List<StatusNotification> getStatusNotification(ProductType type, String barcode) {
		TypedQuery<StatusNotification> q = em.createQuery(SELECT_STATUS_AND_STOCK_PER_PRODUCT, StatusNotification.class);
        //q.setParameter("type", "PU");
        q.setParameter("productType", type.getId());
		q.setParameter("barcode", "%"+barcode+"%");

        List<StatusNotification> stocks = q.getResultList();

        try{
            for (StatusNotification stock : stocks) {
                stock.setSellPrice(sellPriceService.getLatestPriceByProduct(stock.getProduct()));
                /*try {
                    KeyValue keyValue = new KeyValue();
                    keyValue.setKey("inventory.product");
                    keyValue.setValue(stock.getProduct().getId());

                    List<KeyValue> filter = new ArrayList<>();
                    filter.add(keyValue);

                    //List<InventoryItemOut> listOut = inventoryItemOutDaoService.getListPaging(0, 1000, "1", "createdDate", filter);
                    //if(listOut!=null && !listOut.isEmpty()){
                    //    int stockOut = listOut.stream().mapToInt(o-> o.getQuantity()).sum();
                    //    stock.setQuantity(stock.getQuantity() - stockOut);
                    //}
                }catch (Exception e){
                    logger.error(e.getMessage());
                }*/
            }
        }catch(Exception e){
            logger.error(e.getMessage());
        }

        return stocks;
    }

	@Override
	public List<StatusNotification> getStatusNotification(ProductType type, String category, String barcode, String group) {
    	String sqlQuery = "";
    	if(StringUtils.isEmpty(category) && StringUtils.isEmpty(group)){
    		sqlQuery = SELECT_STATUS_AND_STOCK_PER_PRODUCT;
		}else if(StringUtils.isEmpty(category)){
    		sqlQuery= SELECT_STATUS_AND_STOCK_PER_PRODUCT_AND_GROUP;
		}else if(StringUtils.isEmpty(group)){
			sqlQuery= SELECT_STATUS_AND_STOCK_PER_PRODUCT_AND_CATEGORY;
		}else{
			sqlQuery= SELECT_STATUS_AND_STOCK_PER_PRODUCT_AND_CATEGORY_AND_GROUP;
		}

		TypedQuery<StatusNotification> q = em.createQuery(sqlQuery, StatusNotification.class);
		//q.setParameter("type", "PU");
		q.setParameter("productType", type.getId());
		q.setParameter("barcode", "%"+barcode+"%");
		if(!StringUtils.isEmpty(category))
			q.setParameter("category", category);

		if(!StringUtils.isEmpty(group))
			q.setParameter("group", group);

		List<StatusNotification> stocks = q.getResultList();

		if(stocks!=null && !stocks.isEmpty()) {
			stocks.sort(Comparator.comparing(s -> s.getExpiredDate()));
			try {
				for (StatusNotification stock : stocks) {
					stock.setSellPrice(sellPriceService.getLatestPriceByProduct(stock.getProduct()));
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return stocks;
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
