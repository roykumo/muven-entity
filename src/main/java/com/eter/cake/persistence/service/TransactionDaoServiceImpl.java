package com.eter.cake.persistence.service;

import com.eter.cake.persistence.entity.ProductType;
import com.eter.cake.persistence.entity.rest.KeyValue;
import com.eter.cake.persistence.entity.rest.SellingReport;
import com.eter.cake.persistence.entity.rest.StatusNotification;
import com.eter.cake.persistence.entity.rest.Transaction;
import com.eter.response.entity.CommonPaging;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class TransactionDaoServiceImpl extends BaseImpl implements TransactionDaoService {

	//private static final String SELECT_TRANSACTIONS = "select source.id, source.transaction_code, source.date, source.type, source.total_price from (  select id, transaction_code, date, 'PEMBELIAN' as type, total_price from inventory where type = 'PU' union all select id, transaction_code, date,  (case when type='ST' then 'STOCK OPNAME' else (case when type='RE' then 'REPACKING' else (  case when type='SA' then 'PENJUALAN ONLINE' else ( case when type='CR' then 'CASH REGISTER' else ( case when type='EX' then 'PENGHAPUSAN' end )  end )  end ) end) end) as type, total_price from inventory_out  ) source";
	private static final String SELECT_TRANSACTIONS = "select source.id, source.transaction_code, source.date, source.type, source.total_price, source.product_type from (  select i.id, i.transaction_code, i.date, 'PEMBELIAN' as type, i.total_price, pt.description as product_type from inventory i left join product_type pt on i.product_type = pt.id where i.type='PU' union all select o.id, o.transaction_code, o.date,  (case when o.type='ST' then 'STOCK OPNAME' else (case when o.type='RE' then 'REPACKING' else (  case when o.type='SA' then 'PENJUALAN ONLINE' else ( case when o.type='CR' then 'CASH REGISTER' else ( case when o.type='EX' then 'PENGHAPUSAN' end ) end ) end ) end) end) as type, o.total_price, (case when o.type='EX' then (select description from product_type where id = o.product_type) else pt.description end) as product_type from inventory_out o left join inventory i on o.inventory_in = i.id left join product_type pt on i.product_type = pt.id  ) source";

	@Override
	public CommonPaging<Transaction> getTransactions(int pageSize, int page, String sortDir, String sort, List<KeyValue> filter) {
		Query q = em.createNativeQuery(SELECT_TRANSACTIONS, Transaction.class);
		//q.setParameter("typeId", type.getId());
		//q.setParameter("barcode", "%"+barcode+"%");
		//q.setParameter("type", "PU");

		List<Transaction> listTrx = q.getResultList();

		CommonPaging<Transaction> paging = new CommonPaging<Transaction>();
		paging.setPage(page);
		paging.setRowPerPage(pageSize);
		paging.setTotalData(listTrx!=null ? listTrx.size() : 0);
		paging.setData(listTrx);

		return paging;
	}

	private static final String SELECT_SELLING_REPORT_BY_PRODUCT_TYPE = "SELECT new com.eter.cake.persistence.entity.rest.SellingReport (ito.id, ito.product, count(1), (sum(ito.quantity * ito.sellPriceTrx))) FROM InventoryItemOutEntity ito WHERE ito.product.category.type.id =:type AND MONTH(ito.inventoryOut.date) =:month AND YEAR(ito.inventoryOut.date) =:year AND ito.inventoryOut.type in ('CR','SA') GROUP BY ito.product.id ORDER BY ito.product.category.orderNo ASC";

	@Override
	public List<SellingReport> getSellingReport(ProductType type, int month, int year){
		TypedQuery<SellingReport> q = em.createQuery(SELECT_SELLING_REPORT_BY_PRODUCT_TYPE, SellingReport.class);
		q.setParameter("type", type.getId());
		q.setParameter("month", month);
		q.setParameter("year", year);

		List<SellingReport> listSellingReport = q.getResultList();

		return listSellingReport;
	}
}
