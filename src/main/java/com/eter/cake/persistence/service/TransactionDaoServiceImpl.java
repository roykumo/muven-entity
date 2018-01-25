package com.eter.cake.persistence.service;

import com.eter.cake.persistence.entity.rest.KeyValue;
import com.eter.cake.persistence.entity.rest.Transaction;
import com.eter.response.entity.CommonPaging;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class TransactionDaoServiceImpl extends BaseImpl implements TransactionDaoService {

	private static final String SELECT_TRANSACTIONS = "select source.id, source.transaction_code, source.date, source.type, source.total_price from (  select id, transaction_code, date, 'PEMBELIAN' as type, total_price from inventory union all select id, transaction_code, date,  (case when type='ST' then 'STOCK OPNAME' else (case when type='RE' then 'REPACKING' else (  case when type='SA' then 'PENJUALAN ONLINE' else ( case when type='CR' then 'CASH REGISTER' else ( case when type='EX' then 'PENGHAPUSAN' end )  end )  end ) end) end) as type, total_price from inventory_out  ) source";

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

}
