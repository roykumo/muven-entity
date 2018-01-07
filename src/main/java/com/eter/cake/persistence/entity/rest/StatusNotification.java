package com.eter.cake.persistence.entity.rest;

import com.eter.cake.persistence.entity.Product;
import com.eter.cake.persistence.entity.SellPrice;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@NamedNativeQueries({
		@NamedNativeQuery(
				name	=	"getStatusNotifications",
				//query	=	"select new com.eter.cake.persistence.entity.rest.StatusNotification(p.id as 'product.id', p.name as 'product.name', p.code as 'product.code', p.barcode as 'product.barcode', sp.id as 'sellPrice.id', sp.date as 'sellPrice.date', sp.buy_price as 'sellPrice.buy_price', sp.selling_price as 'sellPrice.selling_price', sp.sale as 'sellPrice.sale', sum(case when datediff(now(), it.expired_date) <= p.alert_red then it.quantity else 0 end) as quantity_red,  sum(case when datediff(now(), it.expired_date) > p.alert_red and datediff(now(), it.expired_date) <= p.alert_yellow then it.quantity else 0 end) as quantity_yellow,  sum(case when datediff(now(), it.expired_date) > p.alert_yellow and datediff(now(), it.expired_date) <= p.alert_green then it.quantity else 0 end) as quantity_green,  sum(case when datediff(now(), it.expired_date) > p.alert_green and datediff(now(), it.expired_date) <= p.alert_blue then it.quantity else 0 end) as quantity_blue,  coalesce(ito.quantity_out, 0) as quantity_out, sum(it.purchase_price * it.quantity) as buy_price,  min(it.expired_date) as expired_date) from inventory_item it inner join inventory i on it.inventory = i.id  inner join product p on it.product = p.id inner join product_type pt on p.type = pt.id left join sell_price sp on sp.product = p.id left join ( select po.id as po_id, sum(itout.quantity) as quantity_out from inventory_out iout  left join inventory_item_out itout on itout.inventory_out = iout.id inner join product po on itout.product = po.id group by po.id ) ito on p.id = ito.po_id where p.active_flag = 1 and i.type=:type and p.type =:productType group by p.id;",
				//query	=	"select p.id as id, p.id as 'product.id', p.name as 'product.name', p.code as 'product.code', p.barcode as 'product.barcode', sp.id as 'sellPrice.id', sp.date as 'sellPrice.date', sp.buy_price as 'sellPrice.buy_price', sp.selling_price as 'sellPrice.selling_price', sp.sale as 'sellPrice.sale', sum(case when datediff(now(), it.expired_date) <= p.alert_red then it.quantity else 0 end) as quantity_red,  sum(case when datediff(now(), it.expired_date) > p.alert_red and datediff(now(), it.expired_date) <= p.alert_yellow then it.quantity else 0 end) as quantity_yellow,  sum(case when datediff(now(), it.expired_date) > p.alert_yellow and datediff(now(), it.expired_date) <= p.alert_green then it.quantity else 0 end) as quantity_green,  sum(case when datediff(now(), it.expired_date) > p.alert_green and datediff(now(), it.expired_date) <= p.alert_blue then it.quantity else 0 end) as quantity_blue,  coalesce(ito.quantity_out, 0) as quantity_out, sum(it.purchase_price * it.quantity) as buy_price,  min(it.expired_date) as expired_date from inventory_item it inner join inventory i on it.inventory = i.id  inner join product p on it.product = p.id inner join product_type pt on p.type = pt.id left join sell_price sp on sp.product = p.id left join ( select po.id as po_id, sum(itout.quantity) as quantity_out from inventory_out iout  left join inventory_item_out itout on itout.inventory_out = iout.id inner join product po on itout.product = po.id group by po.id ) ito on p.id = ito.po_id where p.active_flag = 1 and i.type=:type and p.type =:productType group by p.id;",
				query	=	"select StatusNotification(p.id as id, new com.eter.cake.persistence.entity.rest.Product(p.id as id, p.code as code, p.barcode as barcode, new ProductType(), p.name as name, p.alert_red, p.alert_yellow, p.alert_green, p.alert_blue ) as product, sp.id as 'sellPrice.id', sp.date as 'sellPrice.date', sp.buy_price as 'sellPrice.buy_price', sp.selling_price as 'sellPrice.selling_price', sp.sale as 'sellPrice.sale', sum(case when datediff(now(), it.expired_date) <= p.alert_red then it.quantity else 0 end) as quantity_red,  sum(case when datediff(now(), it.expired_date) > p.alert_red and datediff(now(), it.expired_date) <= p.alert_yellow then it.quantity else 0 end) as quantity_yellow,  sum(case when datediff(now(), it.expired_date) > p.alert_yellow and datediff(now(), it.expired_date) <= p.alert_green then it.quantity else 0 end) as quantity_green,  sum(case when datediff(now(), it.expired_date) > p.alert_green and datediff(now(), it.expired_date) <= p.alert_blue then it.quantity else 0 end) as quantity_blue,  coalesce(ito.quantity_out, 0) as quantity_out, sum(it.purchase_price * it.quantity) as buy_price,  min(it.expired_date) as expired_date) from inventory_item it inner join inventory i on it.inventory = i.id  inner join product p on it.product = p.id inner join product_type pt on p.type = pt.id left join sell_price sp on sp.product = p.id left join ( select po.id as po_id, sum(itout.quantity) as quantity_out from inventory_out iout  left join inventory_item_out itout on itout.inventory_out = iout.id inner join product po on itout.product = po.id group by po.id ) ito on p.id = ito.po_id where p.active_flag = 1 and i.type=:type and p.type =:productType group by p.id;",
				resultClass=StatusNotification.class
		)
})
public class StatusNotification {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@ManyToOne
	@JoinColumn(name="product")
	private Product product;

	@Column
	private int quantityRed;
	@Column
	private int quantityYellow;
	@Column
	private int quantityGreen;
	@Column
	private int quantityBlue;
	@Column
	private int quantityOut;
	@Column
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="GMT+7")
	private Date expiredDate;
	@Column
	private BigDecimal buyPrice;
	@ManyToOne
	@JoinColumn(name="sell_price")
	private SellPrice sellPrice;

	public StatusNotification(){}

	public StatusNotification(String id, Product product, long quantityRed, long quantityYellow, long quantityGreen, long quantityBlue, long quantityOut, BigDecimal buyPrice, Date expiredDate){
		this.product = product;
		this.quantityRed = Integer.parseInt(String.valueOf(quantityRed));
		this.quantityYellow = Integer.parseInt(String.valueOf(quantityYellow));
		this.quantityGreen = Integer.parseInt(String.valueOf(quantityGreen));
		this.quantityBlue = Integer.parseInt(String.valueOf(quantityBlue));
		this.quantityOut = Integer.parseInt(String.valueOf(quantityOut));
		this.buyPrice = buyPrice;
		this.expiredDate = expiredDate;
	}

	public StatusNotification(String id, Product product, long quantityRed, long quantityYellow, long quantityGreen, long quantityBlue, long quantityOut, BigDecimal buyPrice, Date expiredDate, SellPrice sellPrice){
		this.product = product;
		this.quantityRed = Integer.parseInt(String.valueOf(quantityRed));
		this.quantityYellow = Integer.parseInt(String.valueOf(quantityYellow));
		this.quantityGreen = Integer.parseInt(String.valueOf(quantityGreen));
		this.quantityBlue = Integer.parseInt(String.valueOf(quantityBlue));
		this.quantityOut = Integer.parseInt(String.valueOf(quantityOut));
		this.buyPrice = buyPrice;
		this.expiredDate = expiredDate;
		this.sellPrice = sellPrice;
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Date getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	public BigDecimal getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}
	public SellPrice getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(SellPrice sellPrice) {
		this.sellPrice = sellPrice;
	}

	public int getQuantityRed() {
		return quantityRed;
	}
	public void setQuantityRed(int quantityRed) {
		this.quantityRed = quantityRed;
	}
	public int getQuantityYellow() {
		return quantityYellow;
	}
	public void setQuantityYellow(int quantityYellow) {
		this.quantityYellow = quantityYellow;
	}
	public int getQuantityGreen() {
		return quantityGreen;
	}
	public void setQuantityGreen(int quantityGreen) {
		this.quantityGreen = quantityGreen;
	}
	public int getQuantityBlue() {
		return quantityBlue;
	}
	public void setQuantityBlue(int quantityBlue) {
		this.quantityBlue = quantityBlue;
	}
	public int getQuantityOut() {
		return quantityOut;
	}
	public void setQuantityOut(int quantityOut) {
		this.quantityOut = quantityOut;
	}
}
